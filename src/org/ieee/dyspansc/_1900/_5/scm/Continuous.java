//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.12.11 at 01:08:10 PM IST 
//


package org.ieee.dyspansc._1900._5.scm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Continuous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Continuous">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bandList" type="{}BandList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Continuous", propOrder = {
    "bandList"
})
public class Continuous {

    @XmlElement(required = true)
    protected BandList bandList;

    /**
     * Gets the value of the bandList property.
     * 
     * @return
     *     possible object is
     *     {@link BandList }
     *     
     */
    public BandList getBandList() {
        return bandList;
    }

    /**
     * Sets the value of the bandList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BandList }
     *     
     */
    public void setBandList(BandList value) {
        this.bandList = value;
    }

}