package org.yal.test.banane.app.modele;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author YLE 1 oct. 2019
 * 
 */
@Entity
@Table(name = "t_destinataire_dst", uniqueConstraints = { @UniqueConstraint(columnNames = { "dst_nom", "dst_code_postal", "dst_adresse", "dst_pays", "dst_ville" }) })
public class Destinataire implements Cloneable
{
  @Id
  @SequenceGenerator(name = "seq_destinataire", sequenceName = "seq_destinataire", allocationSize = 1)
  @GeneratedValue(generator = "seq_destinataire")
  @Column(name = "dst_id", unique = true, nullable = false)
  private Long   id;
  @Column(name = "dst_nom")
  private String nom;
  @Column(name = "dst_adresse")
  private String adresse;
  @Column(name = "dst_code_postal")
  private String codePostal;
  @Column(name = "dst_ville")
  private String ville;
  @Column(name = "dst_pays")
  private String pays;
  
  @OneToMany( cascade = CascadeType.ALL, targetEntity=Commande.class, mappedBy="destinataire", fetch = FetchType.LAZY )
  private List<Commande> commands = new ArrayList<>();
  
  public Destinataire()
  {
    commands = new ArrayList<Commande>();
  }

  public Destinataire(Destinataire destinataire)
  {
    this.id = destinataire.id;
    this.nom = destinataire.nom;
    this.adresse= destinataire.adresse;
    this.codePostal = destinataire.codePostal;
    this.ville = destinataire.ville;
    this.pays = destinataire.pays;
    this.commands = destinataire.commands;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getNom()
  {
    return nom;
  }

  public void setNom(String nom)
  {
    this.nom = nom;
  }

  public String getAdresse()
  {
    return adresse;
  }

  public void setAdresse(String adresse)
  {
    this.adresse = adresse;
  }

  public String getCodePostal()
  {
    return codePostal;
  }

  public void setCodePostal(String codePostal)
  {
    this.codePostal = codePostal;
  }

  public String getVille()
  {
    return ville;
  }

  public void setVille(String ville)
  {
    this.ville = ville;
  }

  public String getPays()
  {
    return pays;
  }

  public void setPays(String pays)
  {
    this.pays = pays;
  }

  public List<Commande> getCommands()
  {
    return commands;
  }

  public void setCommands(List<Commande> commands)
  {
    this.commands = commands;
  }
  
}