//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.12.11 at 01:08:10 PM IST 
//


package org.ieee.dyspansc._1900._5.scm;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Nature.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Nature">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Persistent"/>
 *     &lt;enumeration value="Fleeting"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Nature")
@XmlEnum
public enum Nature {

    @XmlEnumValue("Persistent")
    PERSISTENT("Persistent"),
    @XmlEnumValue("Fleeting")
    FLEETING("Fleeting");
    private final String value;

    Nature(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Nature fromValue(String v) {
        for (Nature c: Nature.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
