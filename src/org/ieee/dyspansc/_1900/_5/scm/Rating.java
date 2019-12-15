//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.12.11 at 01:08:10 PM IST 
//


package org.ieee.dyspansc._1900._5.scm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Rating complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Rating">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="ratedBW" type="{}positive-double"/>
 *           &lt;element name="bwRatedList" type="{}BWRatedList"/>
 *           &lt;element name="ratedBTP" type="{}positive-double"/>
 *           &lt;element name="btpRatedList" type="{}BTPRatedList"/>
 *           &lt;element name="dcRatedList" type="{}DCRatedList"/>
 *           &lt;element name="hopRated" type="{}HopRated"/>
 *           &lt;element name="porPIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Rating", propOrder = {
    "ratedBW",
    "bwRatedList",
    "ratedBTP",
    "btpRatedList",
    "dcRatedList",
    "hopRated",
    "porPIndex"
})
public class Rating {

    protected Double ratedBW;
    protected BWRatedList bwRatedList;
    protected Double ratedBTP;
    protected BTPRatedList btpRatedList;
    protected DCRatedList dcRatedList;
    protected HopRated hopRated;
    protected Integer porPIndex;

    /**
     * Gets the value of the ratedBW property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRatedBW() {
        return ratedBW;
    }

    /**
     * Sets the value of the ratedBW property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRatedBW(Double value) {
        this.ratedBW = value;
    }

    /**
     * Gets the value of the bwRatedList property.
     * 
     * @return
     *     possible object is
     *     {@link BWRatedList }
     *     
     */
    public BWRatedList getBwRatedList() {
        return bwRatedList;
    }

    /**
     * Sets the value of the bwRatedList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BWRatedList }
     *     
     */
    public void setBwRatedList(BWRatedList value) {
        this.bwRatedList = value;
    }

    /**
     * Gets the value of the ratedBTP property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRatedBTP() {
        return ratedBTP;
    }

    /**
     * Sets the value of the ratedBTP property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRatedBTP(Double value) {
        this.ratedBTP = value;
    }

    /**
     * Gets the value of the btpRatedList property.
     * 
     * @return
     *     possible object is
     *     {@link BTPRatedList }
     *     
     */
    public BTPRatedList getBtpRatedList() {
        return btpRatedList;
    }

    /**
     * Sets the value of the btpRatedList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BTPRatedList }
     *     
     */
    public void setBtpRatedList(BTPRatedList value) {
        this.btpRatedList = value;
    }

    /**
     * Gets the value of the dcRatedList property.
     * 
     * @return
     *     possible object is
     *     {@link DCRatedList }
     *     
     */
    public DCRatedList getDcRatedList() {
        return dcRatedList;
    }

    /**
     * Sets the value of the dcRatedList property.
     * 
     * @param value
     *     allowed object is
     *     {@link DCRatedList }
     *     
     */
    public void setDcRatedList(DCRatedList value) {
        this.dcRatedList = value;
    }

    /**
     * Gets the value of the hopRated property.
     * 
     * @return
     *     possible object is
     *     {@link HopRated }
     *     
     */
    public HopRated getHopRated() {
        return hopRated;
    }

    /**
     * Sets the value of the hopRated property.
     * 
     * @param value
     *     allowed object is
     *     {@link HopRated }
     *     
     */
    public void setHopRated(HopRated value) {
        this.hopRated = value;
    }

    /**
     * Gets the value of the porPIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPorPIndex() {
        return porPIndex;
    }

    /**
     * Sets the value of the porPIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPorPIndex(Integer value) {
        this.porPIndex = value;
    }

}
