package org.yal.test.banane.app.db;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yal.test.banane.app.modele.Commande;
import org.yal.test.banane.app.modele.Destinataire;

@Named
public class CommandeDao
{
  private final static Logger logger = LoggerFactory.getLogger(CommandeDao.class);
  @PersistenceContext
  private EntityManager entityManager;

  @Inject
  public CommandeDao()
  {
  }
  
  public void sauvegardeCommande(Commande commande)
  {
    entityManager.persist(commande);
  }
  
  public void updateCommande(Commande commande)
  {
    entityManager.merge(commande);
  }
  
  public void supprimeCommande(Commande commande)
  {
    entityManager.remove(commande);
  }
  
  public List<Destinataire> listAllDestinataire()
  {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Destinataire> cq = cb.createQuery(Destinataire.class);
    Root<Destinataire> root = cq.from(Destinataire.class);
    cq.select(root);
    
    Query query = entityManager.createQuery(cq);
    
    List<Destinataire> destinataires = query.getResultList();
    return destinataires;
  }
  
  public List<Commande> listCommand(Long id)
  {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Commande> cq = cb.createQuery(Commande.class);
    Root<Commande> root = cq.from(Commande.class);
    cq.select(root);
    
    Query query = entityManager.createQuery(cq);
    
    List<Commande> taches = query.getResultList();
    return taches;
  }
  
  public Commande getCommande(long id)
  {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Commande> cq = cb.createQuery(Commande.class);
    Root<Commande> root = cq.from(Commande.class);
    cq.select(root);
    ParameterExpression<Long> idParam = cb.parameter(Long.class);
    cq.where(cb.equal(root.get("id"), idParam));
    
    Query query = entityManager.createQuery(cq);
    query.setParameter(idParam, id);
    
    Commande tache = (Commande)query.getSingleResult();
    return tache;
  }
  
  public Destinataire getDestinataire(long id)
  {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Destinataire> cq = cb.createQuery(Destinataire.class);
    Root<Destinataire> root = cq.from(Destinataire.class);
    cq.select(root);
    ParameterExpression<Long> idParam = cb.parameter(Long.class);
    cq.where(cb.equal(root.get("id"), idParam));
    
    Query query = entityManager.createQuery(cq);
    query.setParameter(idParam, id);
    
    Destinataire destinataire = (Destinataire)query.getSingleResult();
    return destinataire;
  }
  
  public void sauvegardeDestinataire(Destinataire destinataire)
  {
    entityManager.persist(destinataire);
  }
  
  public void updateDestinataire(Destinataire destinataire)
  {
    entityManager.merge(destinataire);
  }
  
  public void supprimeDestinataire(Destinataire destinataire)
  {
    entityManager.remove(destinataire);
  }
}

