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
 * <p>Java class for LogLinearValue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LogLinearValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="exponent" type="{}one-double"/>
 *           &lt;element name="breakpoint" type="{}positive-double"/>
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
@XmlType(name = "LogLinearValue", propOrder = {
    "exponent",
    "breakpoint"
})
public class LogLinearValue {

    protected Double exponent;
    protected Double breakpoint;

    /**
     * Gets the value of the exponent property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getExponent() {
        return exponent;
    }

    /**
     * Sets the value of the exponent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setExponent(Double value) {
        this.exponent = value;
    }

    /**
     * Gets the value of the breakpoint property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBreakpoint() {
        return breakpoint;
    }

    /**
     * Sets the value of the breakpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBreakpoint(Double value) {
        this.breakpoint = value;
    }

}