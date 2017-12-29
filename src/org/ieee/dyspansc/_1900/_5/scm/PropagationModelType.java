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
 * <p>Java class for PropagationModelType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PropagationModelType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="linear" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="piecewiseLinear" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}PiecewiseLinearType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropagationModelType", propOrder = {
    "linear",
    "piecewiseLinear"
})
public class PropagationModelType {

    protected Double linear;
    protected PiecewiseLinearType piecewiseLinear;

    /**
     * Gets the value of the linear property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLinear() {
        return linear;
    }

    /**
     * Sets the value of the linear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLinear(Double value) {
        this.linear = value;
    }

    /**
     * Gets the value of the piecewiseLinear property.
     * 
     * @return
     *     possible object is
     *     {@link PiecewiseLinearType }
     *     
     */
    public PiecewiseLinearType getPiecewiseLinear() {
        return piecewiseLinear;
    }

    /**
     * Sets the value of the piecewiseLinear property.
     * 
     * @param value
     *     allowed object is
     *     {@link PiecewiseLinearType }
     *     
     */
    public void setPiecewiseLinear(PiecewiseLinearType value) {
        this.piecewiseLinear = value;
    }

}
