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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RatingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RatingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="ratedBW" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="bwRatedList" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}BWRatedListType"/>
 *         &lt;element name="ratedBTP" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="btpRatedList" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}BTPRatedListType"/>
 *         &lt;element name="dcRatedList" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}DCRatedListType"/>
 *         &lt;element name="hopRated" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}HopRatingType"/>
 *         &lt;element name="porpIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RatingType", propOrder = {
    "ratedBW",
    "bwRatedList",
    "ratedBTP",
    "btpRatedList",
    "dcRatedList",
    "hopRated",
    "porpIndex"
})
public class RatingType {

    protected Double ratedBW;
    protected BWRatedListType bwRatedList;
    protected Double ratedBTP;
    protected BTPRatedListType btpRatedList;
    protected DCRatedListType dcRatedList;
    protected HopRatingType hopRated;
    protected Integer porpIndex;

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
     *     {@link BWRatedListType }
     *     
     */
    public BWRatedListType getBwRatedList() {
        return bwRatedList;
    }

    /**
     * Sets the value of the bwRatedList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BWRatedListType }
     *     
     */
    public void setBwRatedList(BWRatedListType value) {
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
     *     {@link BTPRatedListType }
     *     
     */
    public BTPRatedListType getBtpRatedList() {
        return btpRatedList;
    }

    /**
     * Sets the value of the btpRatedList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BTPRatedListType }
     *     
     */
    public void setBtpRatedList(BTPRatedListType value) {
        this.btpRatedList = value;
    }

    /**
     * Gets the value of the dcRatedList property.
     * 
     * @return
     *     possible object is
     *     {@link DCRatedListType }
     *     
     */
    public DCRatedListType getDcRatedList() {
        return dcRatedList;
    }

    /**
     * Sets the value of the dcRatedList property.
     * 
     * @param value
     *     allowed object is
     *     {@link DCRatedListType }
     *     
     */
    public void setDcRatedList(DCRatedListType value) {
        this.dcRatedList = value;
    }

    /**
     * Gets the value of the hopRated property.
     * 
     * @return
     *     possible object is
     *     {@link HopRatingType }
     *     
     */
    public HopRatingType getHopRated() {
        return hopRated;
    }

    /**
     * Sets the value of the hopRated property.
     * 
     * @param value
     *     allowed object is
     *     {@link HopRatingType }
     *     
     */
    public void setHopRated(HopRatingType value) {
        this.hopRated = value;
    }

    /**
     * Gets the value of the porpIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPorpIndex() {
        return porpIndex;
    }

    /**
     * Sets the value of the porpIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPorpIndex(Integer value) {
        this.porpIndex = value;
    }

}
