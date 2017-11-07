
package org.ieee.dyspansc._1900._5.scm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HopRatingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HopRatingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="hoppingData" type="{http://www.ieee.org/DyspanSC/1900/5/SCM}HoppingDataType"/>
 *         &lt;element name="toleranceThreshold" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HopRatingType", propOrder = {
    "hoppingData",
    "toleranceThreshold"
})
public class HopRatingType {

    @XmlElement(required = true)
    protected HoppingDataType hoppingData;
    protected int toleranceThreshold;

    /**
     * Gets the value of the hoppingData property.
     * 
     * @return
     *     possible object is
     *     {@link HoppingDataType }
     *     
     */
    public HoppingDataType getHoppingData() {
        return hoppingData;
    }

    /**
     * Sets the value of the hoppingData property.
     * 
     * @param value
     *     allowed object is
     *     {@link HoppingDataType }
     *     
     */
    public void setHoppingData(HoppingDataType value) {
        this.hoppingData = value;
    }

    /**
     * Gets the value of the toleranceThreshold property.
     * 
     */
    public int getToleranceThreshold() {
        return toleranceThreshold;
    }

    /**
     * Sets the value of the toleranceThreshold property.
     * 
     */
    public void setToleranceThreshold(int value) {
        this.toleranceThreshold = value;
    }

}
