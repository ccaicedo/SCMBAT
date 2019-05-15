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
