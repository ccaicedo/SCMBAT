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
@XmlType(name = "Location", propOrder = {
    "point",
    "pointSurface",
    "circularSurface",
    "polygonSurface",
    "cylinder",
    "polyhedron",
    "path"
})
public class Location {

    protected Point point;
    protected PointSurface pointSurface;
    protected CircularSurface circularSurface;
    protected PolygonSurface polygonSurface;
    protected Cylinder cylinder;
    protected Polyhedron polyhedron;
    protected Path path;

    /**
     * Gets the value of the point property.
     * 
     * @return
     *     possible object is
     *     {@link Point }
     *     
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Sets the value of the point property.
     * 
     * @param value
     *     allowed object is
     *     {@link Point }
     *     
     */
    public void setPoint(Point value) {
        this.point = value;
    }

    /**
     * Gets the value of the pointSurface property.
     * 
     * @return
     *     possible object is
     *     {@link PointSurface }
     *     
     */
    public PointSurface getPointSurface() {
        return pointSurface;
    }

    /**
     * Sets the value of the pointSurface property.
     * 
     * @param value
     *     allowed object is
     *     {@link PointSurface }
     *     
     */
    public void setPointSurface(PointSurface value) {
        this.pointSurface = value;
    }

    /**
     * Gets the value of the circularSurface property.
     * 
     * @return
     *     possible object is
     *     {@link CircularSurface }
     *     
     */
    public CircularSurface getCircularSurface() {
        return circularSurface;
    }

    /**
     * Sets the value of the circularSurface property.
     * 
     * @param value
     *     allowed object is
     *     {@link CircularSurface }
     *     
     */
    public void setCircularSurface(CircularSurface value) {
        this.circularSurface = value;
    }

    /**
     * Gets the value of the polygonSurface property.
     * 
     * @return
     *     possible object is
     *     {@link PolygonSurface }
     *     
     */
    public PolygonSurface getPolygonSurface() {
        return polygonSurface;
    }

    /**
     * Sets the value of the polygonSurface property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolygonSurface }
     *     
     */
    public void setPolygonSurface(PolygonSurface value) {
        this.polygonSurface = value;
    }

    /**
     * Gets the value of the cylinder property.
     * 
     * @return
     *     possible object is
     *     {@link Cylinder }
     *     
     */
    public Cylinder getCylinder() {
        return cylinder;
    }

    /**
     * Sets the value of the cylinder property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cylinder }
     *     
     */
    public void setCylinder(Cylinder value) {
        this.cylinder = value;
    }

    /**
     * Gets the value of the polyhedron property.
     * 
     * @return
     *     possible object is
     *     {@link Polyhedron }
     *     
     */
    public Polyhedron getPolyhedron() {
        return polyhedron;
    }

    /**
     * Sets the value of the polyhedron property.
     * 
     * @param value
     *     allowed object is
     *     {@link Polyhedron }
     *     
     */
    public void setPolyhedron(Polyhedron value) {
        this.polyhedron = value;
    }

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link Path }
     *     
     */
    public Path getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link Path }
     *     
     */
    public void setPath(Path value) {
        this.path = value;
    }

}
