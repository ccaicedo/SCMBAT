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
 * <p>Java class for LocationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LocationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="point" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}PointType"/>
 *         &lt;element name="pointSurface" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}PointSurfaceType"/>
 *         &lt;element name="circularSurface" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}CircularSurfaceType"/>
 *         &lt;element name="polygonSurface" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}PolygonSurfaceType"/>
 *         &lt;element name="cylinder" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}CylinderType"/>
 *         &lt;element name="polyhedron" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}PolyhedronType"/>
 *         &lt;element name="path" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}PathType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocationType", propOrder = {
    "point",
    "pointSurface",
    "circularSurface",
    "polygonSurface",
    "cylinder",
    "polyhedron",
    "path"
})
public class LocationType {

    protected PointType point;
    protected PointSurfaceType pointSurface;
    protected CircularSurfaceType circularSurface;
    protected PolygonSurfaceType polygonSurface;
    protected CylinderType cylinder;
    protected PolyhedronType polyhedron;
    protected PathType path;

    /**
     * Gets the value of the point property.
     * 
     * @return
     *     possible object is
     *     {@link PointType }
     *     
     */
    public PointType getPoint() {
        return point;
    }

    /**
     * Sets the value of the point property.
     * 
     * @param value
     *     allowed object is
     *     {@link PointType }
     *     
     */
    public void setPoint(PointType value) {
        this.point = value;
    }

    /**
     * Gets the value of the pointSurface property.
     * 
     * @return
     *     possible object is
     *     {@link PointSurfaceType }
     *     
     */
    public PointSurfaceType getPointSurface() {
        return pointSurface;
    }

    /**
     * Sets the value of the pointSurface property.
     * 
     * @param value
     *     allowed object is
     *     {@link PointSurfaceType }
     *     
     */
    public void setPointSurface(PointSurfaceType value) {
        this.pointSurface = value;
    }

    /**
     * Gets the value of the circularSurface property.
     * 
     * @return
     *     possible object is
     *     {@link CircularSurfaceType }
     *     
     */
    public CircularSurfaceType getCircularSurface() {
        return circularSurface;
    }

    /**
     * Sets the value of the circularSurface property.
     * 
     * @param value
     *     allowed object is
     *     {@link CircularSurfaceType }
     *     
     */
    public void setCircularSurface(CircularSurfaceType value) {
        this.circularSurface = value;
    }

    /**
     * Gets the value of the polygonSurface property.
     * 
     * @return
     *     possible object is
     *     {@link PolygonSurfaceType }
     *     
     */
    public PolygonSurfaceType getPolygonSurface() {
        return polygonSurface;
    }

    /**
     * Sets the value of the polygonSurface property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolygonSurfaceType }
     *     
     */
    public void setPolygonSurface(PolygonSurfaceType value) {
        this.polygonSurface = value;
    }

    /**
     * Gets the value of the cylinder property.
     * 
     * @return
     *     possible object is
     *     {@link CylinderType }
     *     
     */
    public CylinderType getCylinder() {
        return cylinder;
    }

    /**
     * Sets the value of the cylinder property.
     * 
     * @param value
     *     allowed object is
     *     {@link CylinderType }
     *     
     */
    public void setCylinder(CylinderType value) {
        this.cylinder = value;
    }

    /**
     * Gets the value of the polyhedron property.
     * 
     * @return
     *     possible object is
     *     {@link PolyhedronType }
     *     
     */
    public PolyhedronType getPolyhedron() {
        return polyhedron;
    }

    /**
     * Sets the value of the polyhedron property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolyhedronType }
     *     
     */
    public void setPolyhedron(PolyhedronType value) {
        this.polyhedron = value;
    }

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link PathType }
     *     
     */
    public PathType getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathType }
     *     
     */
    public void setPath(PathType value) {
        this.path = value;
    }

}
