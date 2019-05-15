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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GainMap", propOrder = {
    "gainMapValue"
})
public class GainMap {

    @XmlElement(required = true)
    protected List<GainMapValue> gainMapValue;

    /**
     * Gets the value of the gainMapValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gainMapValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGainMapValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GainMapValue }
     * 
     * 
     */
    public List<GainMapValue> getGainMapValue() {
        if (gainMapValue == null) {
            gainMapValue = new ArrayList<GainMapValue>();
        }
        return this.gainMapValue;
    }

}
