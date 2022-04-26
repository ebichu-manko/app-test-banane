package org.yal.test.banane.app.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yal.test.banane.app.business.BananeService;
import org.yal.test.banane.app.modele.Commande;
import org.yal.test.banane.app.modele.Destinataire;

@RestController
@RequestMapping(value = "/rest/v1")
public class APIRestManagement
{
  private final static Logger logger = LoggerFactory.getLogger(APIRestManagement.class);
  private BananeService       service;
  
  @Inject
  public APIRestManagement(BananeService service)
  {
    this.service = service;
  }
  
  @RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Map init()
  {
    logger.debug("init ok");
    Map<String, Object> o = new HashMap<>();
    o.put("message", "api are ok");
    
    return o;
  }
  
  @RequestMapping(value = "/dest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Destinataire createDestinataire(@RequestBody Destinataire destinataire)
  {
    logger.debug("createDestinataire -> /dest", destinataire);
    return service.creationDestinataire(destinataire);
  }
  
  @RequestMapping(value = "/dest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Destinataire> listAllDestinataire()
  {
    logger.debug("listAllDestinataire - '/dest'");
    return service.listAllDestinataire();
  }
  
  @RequestMapping(value = "/dest/{destinataireId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Destinataire getDestinataire(@PathVariable("destinataireId") long destinataireId)
  {
    logger.debug("listAllDestinataire - '/dest'");
    return service.getDestinataireById(destinataireId);
  }
  
  @RequestMapping(value = "/dest/{destinataireId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void deleteDestinataire(@PathVariable("destinataireId") long destinataireId)
  {
    logger.debug("deleteDestinataire - '/dest/{}'", destinataireId);
    service.deleteDestinataire(destinataireId);
  }
  
  @RequestMapping(value = "/dest/{destinataireId}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
  public void modificationDestinataire(@PathVariable("destinataireId") long destinataireId, @RequestBody Destinataire destinataire)
  {
    logger.debug("modifyDestinataire - '/dest' - destinataireId={}", destinataireId);
    service.updateDestinataire(destinataireId, destinataire);
  }
  
  @RequestMapping(value = "/dest/{destinataireId}/commande", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Commande> modifyDestinataire(@PathVariable("destinataireId") long destinataireId)
  {
    logger.debug("lister les commandes d'un destinataire - '/commande/{destinataireId}' - destinataireId={}", destinataireId);
    return service.listerLesCommandes(destinataireId);
  }
  
  @RequestMapping(value = "/dest/{destinataireId}/commande", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Commande creationCommande(@PathVariable("destinataireId") long destinataireId, @RequestBody Commande commande)
  {
    logger.debug("creation d'une commande d'une commande - POST- '/commande/{destinataireId={}}' - commande={}", destinataireId, commande);
    return service.creationCommande(destinataireId, commande);
  }
  
  @RequestMapping(value = "/commande/{commandeId}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
  public void modificationCommande(@PathVariable("commandeId") long commandeId,
                             @RequestBody Commande commande)
  {
    logger.debug("modifyDestinataire - '/dest' - commandeId={}", commandeId);
    service.updateCommande(commandeId, commande);
  }
  
  @RequestMapping(value = "/commande/{commandeId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void suppressionCommande(@PathVariable("commandeId") long commandeId)
  {
    logger.debug("suppressionCommande - '/commande' - commandeId={}", commandeId);
    service.deleteCommande(commandeId);
  }
}
