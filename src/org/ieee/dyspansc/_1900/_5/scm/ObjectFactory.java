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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.ieee.dyspansc._1900._5.scm package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TxModel_QNAME = new QName("http://www.ieee.org/DyspanSC/1900/5/SCM", "txModel");
    private final static QName _RxModel_QNAME = new QName("http://www.ieee.org/DyspanSC/1900/5/SCM", "rxModel");
    private final static QName _SystemModel_QNAME = new QName("http://www.ieee.org/DyspanSC/1900/5/SCM", "systemModel");
    private final static QName _ScmSet_QNAME = new QName("http://www.ieee.org/DyspanSC/1900/5/SCM", "scmSet");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.ieee.dyspansc._1900._5.scm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SystemModelType }
     * 
     */
    public SystemModelType createSystemModelType() {
        return new SystemModelType();
    }

    /**
     * Create an instance of {@link SCMSetType }
     * 
     */
    public SCMSetType createSCMSetType() {
        return new SCMSetType();
    }

    /**
     * Create an instance of {@link TxModelType }
     * 
     */
    public TxModelType createTxModelType() {
        return new TxModelType();
    }

    /**
     * Create an instance of {@link RxModelType }
     * 
     */
    public RxModelType createRxModelType() {
        return new RxModelType();
    }

    /**
     * Create an instance of {@link UberModelType }
     * 
     */
    public UberModelType createUberModelType() {
        return new UberModelType();
    }

    /**
     * Create an instance of {@link ReferencePowerType }
     * 
     */
    public ReferencePowerType createReferencePowerType() {
        return new ReferencePowerType();
    }

    /**
     * Create an instance of {@link BandType }
     * 
     */
    public BandType createBandType() {
        return new BandType();
    }

    /**
     * Create an instance of {@link ConfidenceType }
     * 
     */
    public ConfidenceType createConfidenceType() {
        return new ConfidenceType();
    }

    /**
     * Create an instance of {@link SpectrumMaskType }
     * 
     */
    public SpectrumMaskType createSpectrumMaskType() {
        return new SpectrumMaskType();
    }

    /**
     * Create an instance of {@link SideType }
     * 
     */
    public SideType createSideType() {
        return new SideType();
    }

    /**
     * Create an instance of {@link ScanValueType }
     * 
     */
    public ScanValueType createScanValueType() {
        return new ScanValueType();
    }

    /**
     * Create an instance of {@link PropagationModelType }
     * 
     */
    public PropagationModelType createPropagationModelType() {
        return new PropagationModelType();
    }

    /**
     * Create an instance of {@link BandListType }
     * 
     */
    public BandListType createBandListType() {
        return new BandListType();
    }

    /**
     * Create an instance of {@link PolygonSurfaceType }
     * 
     */
    public PolygonSurfaceType createPolygonSurfaceType() {
        return new PolygonSurfaceType();
    }

    /**
     * Create an instance of {@link SCMPropagationMapType }
     * 
     */
    public SCMPropagationMapType createSCMPropagationMapType() {
        return new SCMPropagationMapType();
    }

    /**
     * Create an instance of {@link SCMScheduleType }
     * 
     */
    public SCMScheduleType createSCMScheduleType() {
        return new SCMScheduleType();
    }

    /**
     * Create an instance of {@link DCRatingType }
     * 
     */
    public DCRatingType createDCRatingType() {
        return new DCRatingType();
    }

    /**
     * Create an instance of {@link BTPRatingType }
     * 
     */
    public BTPRatingType createBTPRatingType() {
        return new BTPRatingType();
    }

    /**
     * Create an instance of {@link CircularSurfaceType }
     * 
     */
    public CircularSurfaceType createCircularSurfaceType() {
        return new CircularSurfaceType();
    }

    /**
     * Create an instance of {@link PolyhedronType }
     * 
     */
    public PolyhedronType createPolyhedronType() {
        return new PolyhedronType();
    }

    /**
     * Create an instance of {@link PointSurfaceType }
     * 
     */
    public PointSurfaceType createPointSurfaceType() {
        return new PointSurfaceType();
    }

    /**
     * Create an instance of {@link IntermodulationMaskType }
     * 
     */
    public IntermodulationMaskType createIntermodulationMaskType() {
        return new IntermodulationMaskType();
    }

    /**
     * Create an instance of {@link GainMapType }
     * 
     */
    public GainMapType createGainMapType() {
        return new GainMapType();
    }

    /**
     * Create an instance of {@link PropMapValueType }
     * 
     */
    public PropMapValueType createPropMapValueType() {
        return new PropMapValueType();
    }

    /**
     * Create an instance of {@link FrequencyListType }
     * 
     */
    public FrequencyListType createFrequencyListType() {
        return new FrequencyListType();
    }

    /**
     * Create an instance of {@link CycleType }
     * 
     */
    public CycleType createCycleType() {
        return new CycleType();
    }

    /**
     * Create an instance of {@link POrPParametersType }
     * 
     */
    public POrPParametersType createPOrPParametersType() {
        return new POrPParametersType();
    }

    /**
     * Create an instance of {@link SCMPlatformType }
     * 
     */
    public SCMPlatformType createSCMPlatformType() {
        return new SCMPlatformType();
    }

    /**
     * Create an instance of {@link SCMLocationType }
     * 
     */
    public SCMLocationType createSCMLocationType() {
        return new SCMLocationType();
    }

    /**
     * Create an instance of {@link PropMapType }
     * 
     */
    public PropMapType createPropMapType() {
        return new PropMapType();
    }

    /**
     * Create an instance of {@link SCMPowerMapType }
     * 
     */
    public SCMPowerMapType createSCMPowerMapType() {
        return new SCMPowerMapType();
    }

    /**
     * Create an instance of {@link PiecewiseLinearType }
     * 
     */
    public PiecewiseLinearType createPiecewiseLinearType() {
        return new PiecewiseLinearType();
    }

    /**
     * Create an instance of {@link RelativeToPlatformType }
     * 
     */
    public RelativeToPlatformType createRelativeToPlatformType() {
        return new RelativeToPlatformType();
    }

    /**
     * Create an instance of {@link CylinderType }
     * 
     */
    public CylinderType createCylinderType() {
        return new CylinderType();
    }

    /**
     * Create an instance of {@link PathPointType }
     * 
     */
    public PathPointType createPathPointType() {
        return new PathPointType();
    }

    /**
     * Create an instance of {@link TowardReferencePointType }
     * 
     */
    public TowardReferencePointType createTowardReferencePointType() {
        return new TowardReferencePointType();
    }

    /**
     * Create an instance of {@link BWRatingType }
     * 
     */
    public BWRatingType createBWRatingType() {
        return new BWRatingType();
    }

    /**
     * Create an instance of {@link OrientationType }
     * 
     */
    public OrientationType createOrientationType() {
        return new OrientationType();
    }

    /**
     * Create an instance of {@link SCMMaskType }
     * 
     */
    public SCMMaskType createSCMMaskType() {
        return new SCMMaskType();
    }

    /**
     * Create an instance of {@link InflectionPointType }
     * 
     */
    public InflectionPointType createInflectionPointType() {
        return new InflectionPointType();
    }

    /**
     * Create an instance of {@link SCMPolygonType }
     * 
     */
    public SCMPolygonType createSCMPolygonType() {
        return new SCMPolygonType();
    }

    /**
     * Create an instance of {@link BWRatedListType }
     * 
     */
    public BWRatedListType createBWRatedListType() {
        return new BWRatedListType();
    }

    /**
     * Create an instance of {@link UnderlayMaskType }
     * 
     */
    public UnderlayMaskType createUnderlayMaskType() {
        return new UnderlayMaskType();
    }

    /**
     * Create an instance of {@link DCRatedListType }
     * 
     */
    public DCRatedListType createDCRatedListType() {
        return new DCRatedListType();
    }

    /**
     * Create an instance of {@link BoundaryType }
     * 
     */
    public BoundaryType createBoundaryType() {
        return new BoundaryType();
    }

    /**
     * Create an instance of {@link HopRatingType }
     * 
     */
    public HopRatingType createHopRatingType() {
        return new HopRatingType();
    }

    /**
     * Create an instance of {@link RatingType }
     * 
     */
    public RatingType createRatingType() {
        return new RatingType();
    }

    /**
     * Create an instance of {@link ScanningRegionType }
     * 
     */
    public ScanningRegionType createScanningRegionType() {
        return new ScanningRegionType();
    }

    /**
     * Create an instance of {@link BTPRatedListType }
     * 
     */
    public BTPRatedListType createBTPRatedListType() {
        return new BTPRatedListType();
    }

    /**
     * Create an instance of {@link AntennaHeightType }
     * 
     */
    public AntennaHeightType createAntennaHeightType() {
        return new AntennaHeightType();
    }

    /**
     * Create an instance of {@link SCMPolicyOrProtocolType }
     * 
     */
    public SCMPolicyOrProtocolType createSCMPolicyOrProtocolType() {
        return new SCMPolicyOrProtocolType();
    }

    /**
     * Create an instance of {@link GainMapValueType }
     * 
     */
    public GainMapValueType createGainMapValueType() {
        return new GainMapValueType();
    }

    /**
     * Create an instance of {@link PathType }
     * 
     */
    public PathType createPathType() {
        return new PathType();
    }

    /**
     * Create an instance of {@link SCMMapType }
     * 
     */
    public SCMMapType createSCMMapType() {
        return new SCMMapType();
    }

    /**
     * Create an instance of {@link HoppingDataType }
     * 
     */
    public HoppingDataType createHoppingDataType() {
        return new HoppingDataType();
    }

    /**
     * Create an instance of {@link PointType }
     * 
     */
    public PointType createPointType() {
        return new PointType();
    }

    /**
     * Create an instance of {@link LocationType }
     * 
     */
    public LocationType createLocationType() {
        return new LocationType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TxModelType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ieee.org/DyspanSC/1900/5/SCM", name = "txModel")
    public JAXBElement<TxModelType> createTxModel(TxModelType value) {
        return new JAXBElement<TxModelType>(_TxModel_QNAME, TxModelType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RxModelType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ieee.org/DyspanSC/1900/5/SCM", name = "rxModel")
    public JAXBElement<RxModelType> createRxModel(RxModelType value) {
        return new JAXBElement<RxModelType>(_RxModel_QNAME, RxModelType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SystemModelType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ieee.org/DyspanSC/1900/5/SCM", name = "systemModel")
    public JAXBElement<SystemModelType> createSystemModel(SystemModelType value) {
        return new JAXBElement<SystemModelType>(_SystemModel_QNAME, SystemModelType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SCMSetType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ieee.org/DyspanSC/1900/5/SCM", name = "scmSet")
    public JAXBElement<SCMSetType> createScmSet(SCMSetType value) {
        return new JAXBElement<SCMSetType>(_ScmSet_QNAME, SCMSetType.class, null, value);
    }

}
