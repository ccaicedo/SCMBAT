/*
Copyright (C) 2016 Syracuse University

This file is part of the Spectrum Consumption Model Builder and
Analysis Tool

This program is free software; you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by the
Free Software Foundation; either version 3 of the License, or (at your
option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License
along with program.  If not, see <http://www.gnu.org/licenses/>.

*/

package org.ieee.dyspansc._1900._5.scm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SCMPolicyOrProtocol complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SCMPolicyOrProtocol">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="porPName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="porPParameters" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}PorPParameters" minOccurs="0"/>
 *         &lt;element name="porPIndex" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SCMPolicyOrProtocol", propOrder = {
    "porPName",
    "porPParameters",
    "porPIndex"
})
public class SCMPolicyOrProtocol {

    @XmlElement(required = true)
    protected String porPName;
    protected PorPParameters porPParameters;
    protected Integer porPIndex;

    /**
     * Gets the value of the porPName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPorPName() {
        return porPName;
    }

    /**
     * Sets the value of the porPName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPorPName(String value) {
        this.porPName = value;
    }

    /**
     * Gets the value of the porPParameters property.
     * 
     * @return
     *     possible object is
     *     {@link PorPParameters }
     *     
     */
    public PorPParameters getPorPParameters() {
        return porPParameters;
    }

    /**
     * Sets the value of the porPParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link PorPParameters }
     *     
     */
    public void setPorPParameters(PorPParameters value) {
        this.porPParameters = value;
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
