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
 * <p>Java class for Parameter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Parameter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="stringID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;choice>
 *           &lt;element name="stringParameter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="numberParameter" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
@XmlType(name = "Parameter", propOrder = {
    "stringID",
    "stringParameter",
    "numberParameter"
})
public class Parameter {

    @XmlElement(required = true)
    protected String stringID;
    protected String stringParameter;
    protected Double numberParameter;

    /**
     * Gets the value of the stringID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStringID() {
        return stringID;
    }

    /**
     * Sets the value of the stringID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStringID(String value) {
        this.stringID = value;
    }

    /**
     * Gets the value of the stringParameter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStringParameter() {
        return stringParameter;
    }

    /**
     * Sets the value of the stringParameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStringParameter(String value) {
        this.stringParameter = value;
    }

    /**
     * Gets the value of the numberParameter property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getNumberParameter() {
        return numberParameter;
    }

    /**
     * Sets the value of the numberParameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setNumberParameter(Double value) {
        this.numberParameter = value;
    }

}
