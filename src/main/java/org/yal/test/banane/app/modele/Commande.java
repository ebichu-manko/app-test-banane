package org.yal.test.banane.app.modele;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author YLE 1 oct. 2019
 * 
 */
@Entity
@Table(name = "t_commande_com")
public class Commande implements Cloneable
{
  @Id
  @SequenceGenerator(name = "seq_commande", sequenceName = "seq_commande", allocationSize = 1)
  @GeneratedValue(generator = "seq_commande")
  @Column(name = "com_id", unique = true, nullable = false)
  private Long   id;
  @Column(name = "com_date")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="UTC")
  private Date date;
  @Column(name = "com_quantite")
  private Long quantite;
  @Column(name = "com_prix")
  private Long prix;
  @ManyToOne @JoinColumn( name="com_dst_id", nullable=false )
  private Destinataire destinataire;
  
  public Commande()
  {}

  public Commande(Commande commande)
  {
    this.id = commande.id;
    this.date = commande.date;
    this.quantite = commande.quantite;
    this.prix = commande.prix;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public Date getDate()
  {
    return date;
  }

  public void setDate(Date date)
  {
    this.date = date;
  }

  public Long getQuantite()
  {
    return quantite;
  }

  public void setQuantite(Long quantite)
  {
    this.quantite = quantite;
  }

  public Long getPrix()
  {
    return prix;
  }

  public void setPrix(Long prix)
  {
    this.prix = prix;
  }

  public Destinataire getDestinataire()
  {
    return destinataire;
  }

  public void setDestinataire(Destinataire destinataire)
  {
    this.destinataire = destinataire;
  }
  
 
}