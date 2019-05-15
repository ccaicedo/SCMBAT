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
@XmlType(name = "Orientation", propOrder = {
    "surface",
    "relativeToPlatform",
    "towardReferencePoint"
})
public class Orientation {

    protected Boolean surface;
    protected RelativeToPlatform relativeToPlatform;
    protected TowardReferencePoint towardReferencePoint;

    /**
     * Gets the value of the surface property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSurface() {
        return surface;
    }

    /**
     * Sets the value of the surface property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSurface(Boolean value) {
        this.surface = value;
    }

    /**
     * Gets the value of the relativeToPlatform property.
     * 
     * @return
     *     possible object is
     *     {@link RelativeToPlatform }
     *     
     */
    public RelativeToPlatform getRelativeToPlatform() {
        return relativeToPlatform;
    }

    /**
     * Sets the value of the relativeToPlatform property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelativeToPlatform }
     *     
     */
    public void setRelativeToPlatform(RelativeToPlatform value) {
        this.relativeToPlatform = value;
    }

    /**
     * Gets the value of the towardReferencePoint property.
     * 
     * @return
     *     possible object is
     *     {@link TowardReferencePoint }
     *     
     */
    public TowardReferencePoint getTowardReferencePoint() {
        return towardReferencePoint;
    }

    /**
     * Sets the value of the towardReferencePoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link TowardReferencePoint }
     *     
     */
    public void setTowardReferencePoint(TowardReferencePoint value) {
        this.towardReferencePoint = value;
    }

}
