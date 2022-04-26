package org.yal.test.banane.app.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.yal.test.banane.app.common.StringHelper;
import org.yal.test.banane.app.db.CommandeDao;
import org.yal.test.banane.app.exception.ValeurPasAutoriseException;
import org.yal.test.banane.app.modele.Commande;
import org.yal.test.banane.app.modele.Destinataire;

@PropertySource("file:${fichier.config}")
@Service
@Transactional
public class BananeService
{
  private final static Logger logger = LoggerFactory.getLogger(BananeService.class);
  
  private CommandeDao         commandeDao;
  private Environment         environment;
  
  @Inject
  public BananeService(CommandeDao traitementDao, Environment environment)
  {
    this.commandeDao = traitementDao;
    this.environment = environment;
  }
  
  public Commande deleteCommande(long id)
  {
    logger.debug("deleteCommande(id={}", id);
    
    try
    {
      Commande commande = commandeDao.getCommande(id);
      commandeDao.supprimeCommande(commande);
      return commande;
    }
    catch (Exception e)
    {
      logger.error("probleme lors de la suppression de la tache dans la base", e);
      throw e;
    }
  }
  
  public Destinataire deleteDestinataire(long id)
  {
    logger.debug("deleteDestinataire(id={}", id);
    
    try
    {
      Destinataire destinataire = commandeDao.getDestinataire(id);
      commandeDao.supprimeDestinataire(destinataire);
      return destinataire;
    }
    catch (Exception e)
    {
      logger.error("probleme lors de la suppression de la tache dans la base", e);
      throw e;
    }
  }
  
  public Commande updateCommande(long commandId, Commande commande)
  {
    logger.debug("updateShortUrl(ShortUrl={}", commande);
    
    try
    {
      Commande commandeFromBase = commandeDao.getCommande(commandId);
      checkCommande(commande);
      
      if (commande.getDate() != null)
        commandeFromBase.setDate(commande.getDate());
      if (commande.getQuantite() != null && commande.getQuantite() != commandeFromBase.getQuantite()) 
      {
        Long quantite = commande.getQuantite();
        
        if (quantite < 0 || quantite > 10_000 || (quantite % 25) != 0)
          throw new ValeurPasAutoriseException("la quantite n'est pas acceptable");

        commandeFromBase.setQuantite(commande.getQuantite());
        commandeFromBase.setPrix(quantite * 250);
      }
      
      commandeDao.updateCommande(commandeFromBase);
      return commande;
    }
    catch (Exception e)
    {
      logger.error("probleme lors de la suppression de la tache dans la base", e);
      throw e;
    }
  }
  
  public Destinataire updateDestinataire(Long idDestinataire, Destinataire destinataire)
  {
    logger.debug("updateDestinataire( idDestinataire={}, Destinataire ={}", idDestinataire, destinataire);
    
    try
    {
      Destinataire destinataireFromBase = commandeDao.getDestinataire(idDestinataire);
      
      if (StringHelper.isNotEmptyOrNull(destinataire.getAdresse()))
        destinataireFromBase.setAdresse(destinataire.getAdresse());
      if (StringHelper.isNotEmptyOrNull(destinataire.getNom()))
        destinataireFromBase.setAdresse(destinataire.getNom());
      if (StringHelper.isNotEmptyOrNull(destinataire.getPays()))
        destinataireFromBase.setPays(destinataire.getPays());
      if (StringHelper.isNotEmptyOrNull(destinataire.getVille()))
        destinataireFromBase.setVille(destinataire.getVille());
      if (StringHelper.isNotEmptyOrNull(destinataire.getCodePostal()))
        destinataireFromBase.setCodePostal(destinataire.getCodePostal());
      
      commandeDao.updateDestinataire(destinataire);
      return destinataire;
    }
    catch (Exception e)
    {
      logger.error("probleme lors de la suppression de la tache dans la base", e);
      throw e;
    }
  }
  
  public Commande creationCommande(Long destinataireId, Commande commande)
  {
    logger.debug("creation d'une commande");
    
    StringHelper.parametreNonNull("quantite", commande.getQuantite());
    StringHelper.parametreNonNull("date", commande.getDate());
    
    checkCommande(commande);
    
    Destinataire destinataire = commandeDao.getDestinataire(destinataireId);
    commande.setDestinataire(destinataire);
    destinataire.getCommands().add(commande);
    commandeDao.sauvegardeCommande(commande);
    Commande cDto = new Commande(commande);
    
    logger.debug("creation of command = {}", cDto);
    
    return cDto;
  }
  
  protected void checkCommande(Commande commande)
  {
    Long quantite = commande.getQuantite();
    
    if (quantite < 0 || quantite > 10_000 || (quantite % 25) != 0)
      throw new ValeurPasAutoriseException("la quantite n'est pas acceptable");
    
    if ( LocalDate.now().atStartOfDay().plusWeeks(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() > commande.getDate().getTime())
      throw new ValeurPasAutoriseException("La date de livraison doit être, au minimum, une semaine dans le futur par rapport à la date du jour");
    
    commande.setPrix(quantite * 250);
  }
  
  public Destinataire creationDestinataire(Destinataire destinataire)
  {
    logger.debug("creation of short url");
    
    StringHelper.parametreNonNull("nom", destinataire.getNom());
    StringHelper.parametreNonNull("adresse", destinataire.getAdresse());
    StringHelper.parametreNonNull("code postal", destinataire.getCodePostal());
    StringHelper.parametreNonNull("ville", destinataire.getVille());
    StringHelper.parametreNonNull("pays", destinataire.getPays());
    
    commandeDao.sauvegardeDestinataire(destinataire);
    logger.debug("creation of destinataire = {}", destinataire);
    Destinataire destDto = new Destinataire(destinataire);
    destDto.setCommands(null);
    
    return destDto;
  }
  
  public List<Destinataire> listAllDestinataire()
  {
    logger.debug("listAllDestinataire()");
    List<Destinataire> taches = commandeDao.listAllDestinataire();
    
    List<Destinataire> destinataires = new ArrayList<>();
    taches.forEach(ligne ->
    {
      Destinataire destinataire = new Destinataire(ligne);
      destinataire.setCommands(null);
      destinataires.add(destinataire);
    });
    return destinataires;
  }
  
  public Destinataire getDestinataireById(Long id)
  {
    logger.debug("listAllShortUrl()");
    Destinataire destinataire = commandeDao.getDestinataire(id);
    
    return destinataire;
  }
  
  public List<Commande> listerLesCommandes(Long idDestinataire)
  {
    logger.debug("listerLesCommandes()");
    List<Commande> taches = commandeDao.getDestinataire(idDestinataire).getCommands();
    
    List<Commande> commandes = new ArrayList<>();
    taches.forEach(ligne ->
    {
      Commande commande = new Commande(ligne);
      commandes.add(commande);
    });
    return commandes;
  }
  
  public Commande getCommande(long id)
  {
    logger.debug("getShortUrl()");
    Commande commande = commandeDao.getCommande(id);
    
    return commande;
  }
}
