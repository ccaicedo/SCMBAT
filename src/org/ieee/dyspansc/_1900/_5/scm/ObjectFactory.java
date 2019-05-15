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

    private final static QName _RxModel_QNAME = new QName("http://www.ieee.org/DyspanSC/1900/5/SCM", "rxModel");
    private final static QName _SystemModel_QNAME = new QName("http://www.ieee.org/DyspanSC/1900/5/SCM", "systemModel");
    private final static QName _TxModel_QNAME = new QName("http://www.ieee.org/DyspanSC/1900/5/SCM", "txModel");
    private final static QName _ScmSet_QNAME = new QName("http://www.ieee.org/DyspanSC/1900/5/SCM", "scmSet");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.ieee.dyspansc._1900._5.scm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RxModel }
     * 
     */
    public RxModel createRxModel() {
        return new RxModel();
    }

    /**
     * Create an instance of {@link SCMSet }
     * 
     */
    public SCMSet createSCMSet() {
        return new SCMSet();
    }

    /**
     * Create an instance of {@link SystemModel }
     * 
     */
    public SystemModel createSystemModel() {
        return new SystemModel();
    }

    /**
     * Create an instance of {@link TxModel }
     * 
     */
    public TxModel createTxModel() {
        return new TxModel();
    }

    /**
     * Create an instance of {@link Confidence }
     * 
     */
    public Confidence createConfidence() {
        return new Confidence();
    }

    /**
     * Create an instance of {@link SpectrumMask }
     * 
     */
    public SpectrumMask createSpectrumMask() {
        return new SpectrumMask();
    }

    /**
     * Create an instance of {@link HopRated }
     * 
     */
    public HopRated createHopRated() {
        return new HopRated();
    }

    /**
     * Create an instance of {@link SCMLocation }
     * 
     */
    public SCMLocation createSCMLocation() {
        return new SCMLocation();
    }

    /**
     * Create an instance of {@link SCMPolicyOrProtocol }
     * 
     */
    public SCMPolicyOrProtocol createSCMPolicyOrProtocol() {
        return new SCMPolicyOrProtocol();
    }

    /**
     * Create an instance of {@link Path }
     * 
     */
    public Path createPath() {
        return new Path();
    }

    /**
     * Create an instance of {@link FrequencyList }
     * 
     */
    public FrequencyList createFrequencyList() {
        return new FrequencyList();
    }

    /**
     * Create an instance of {@link PolygonSurface }
     * 
     */
    public PolygonSurface createPolygonSurface() {
        return new PolygonSurface();
    }

    /**
     * Create an instance of {@link Side }
     * 
     */
    public Side createSide() {
        return new Side();
    }

    /**
     * Create an instance of {@link Point }
     * 
     */
    public Point createPoint() {
        return new Point();
    }

    /**
     * Create an instance of {@link HoppingData }
     * 
     */
    public HoppingData createHoppingData() {
        return new HoppingData();
    }

    /**
     * Create an instance of {@link DCRating }
     * 
     */
    public DCRating createDCRating() {
        return new DCRating();
    }

    /**
     * Create an instance of {@link IntermodulationMask }
     * 
     */
    public IntermodulationMask createIntermodulationMask() {
        return new IntermodulationMask();
    }

    /**
     * Create an instance of {@link PropagationModel }
     * 
     */
    public PropagationModel createPropagationModel() {
        return new PropagationModel();
    }

    /**
     * Create an instance of {@link UnderlayMask }
     * 
     */
    public UnderlayMask createUnderlayMask() {
        return new UnderlayMask();
    }

    /**
     * Create an instance of {@link PiecewiseLinear }
     * 
     */
    public PiecewiseLinear createPiecewiseLinear() {
        return new PiecewiseLinear();
    }

    /**
     * Create an instance of {@link UberModel }
     * 
     */
    public UberModel createUberModel() {
        return new UberModel();
    }

    /**
     * Create an instance of {@link SCMMask }
     * 
     */
    public SCMMask createSCMMask() {
        return new SCMMask();
    }

    /**
     * Create an instance of {@link CircularSurface }
     * 
     */
    public CircularSurface createCircularSurface() {
        return new CircularSurface();
    }

    /**
     * Create an instance of {@link Rating }
     * 
     */
    public Rating createRating() {
        return new Rating();
    }

    /**
     * Create an instance of {@link Cycle }
     * 
     */
    public Cycle createCycle() {
        return new Cycle();
    }

    /**
     * Create an instance of {@link BandList }
     * 
     */
    public BandList createBandList() {
        return new BandList();
    }

    /**
     * Create an instance of {@link ReferencePower }
     * 
     */
    public ReferencePower createReferencePower() {
        return new ReferencePower();
    }

    /**
     * Create an instance of {@link GainMapValue }
     * 
     */
    public GainMapValue createGainMapValue() {
        return new GainMapValue();
    }

    /**
     * Create an instance of {@link Polyhedron }
     * 
     */
    public Polyhedron createPolyhedron() {
        return new Polyhedron();
    }

    /**
     * Create an instance of {@link RelativeToPlatform }
     * 
     */
    public RelativeToPlatform createRelativeToPlatform() {
        return new RelativeToPlatform();
    }

    /**
     * Create an instance of {@link SCMSchedule }
     * 
     */
    public SCMSchedule createSCMSchedule() {
        return new SCMSchedule();
    }

    /**
     * Create an instance of {@link PropMap }
     * 
     */
    public PropMap createPropMap() {
        return new PropMap();
    }

    /**
     * Create an instance of {@link PorPParameters }
     * 
     */
    public PorPParameters createPorPParameters() {
        return new PorPParameters();
    }

    /**
     * Create an instance of {@link InflectionPnt }
     * 
     */
    public InflectionPnt createInflectionPnt() {
        return new InflectionPnt();
    }

    /**
     * Create an instance of {@link SCMPlatform }
     * 
     */
    public SCMPlatform createSCMPlatform() {
        return new SCMPlatform();
    }

    /**
     * Create an instance of {@link Cylinder }
     * 
     */
    public Cylinder createCylinder() {
        return new Cylinder();
    }

    /**
     * Create an instance of {@link AntennaHeight }
     * 
     */
    public AntennaHeight createAntennaHeight() {
        return new AntennaHeight();
    }

    /**
     * Create an instance of {@link SCMPolygon }
     * 
     */
    public SCMPolygon createSCMPolygon() {
        return new SCMPolygon();
    }

    /**
     * Create an instance of {@link ScanValue }
     * 
     */
    public ScanValue createScanValue() {
        return new ScanValue();
    }

    /**
     * Create an instance of {@link SCMPropagationMap }
     * 
     */
    public SCMPropagationMap createSCMPropagationMap() {
        return new SCMPropagationMap();
    }

    /**
     * Create an instance of {@link BTPRating }
     * 
     */
    public BTPRating createBTPRating() {
        return new BTPRating();
    }

    /**
     * Create an instance of {@link BTPRatedList }
     * 
     */
    public BTPRatedList createBTPRatedList() {
        return new BTPRatedList();
    }

    /**
     * Create an instance of {@link BWRatedList }
     * 
     */
    public BWRatedList createBWRatedList() {
        return new BWRatedList();
    }

    /**
     * Create an instance of {@link SCMPowerMap }
     * 
     */
    public SCMPowerMap createSCMPowerMap() {
        return new SCMPowerMap();
    }

    /**
     * Create an instance of {@link Orientation }
     * 
     */
    public Orientation createOrientation() {
        return new Orientation();
    }

    /**
     * Create an instance of {@link Location }
     * 
     */
    public Location createLocation() {
        return new Location();
    }

    /**
     * Create an instance of {@link GainMap }
     * 
     */
    public GainMap createGainMap() {
        return new GainMap();
    }

    /**
     * Create an instance of {@link PointSurface }
     * 
     */
    public PointSurface createPointSurface() {
        return new PointSurface();
    }

    /**
     * Create an instance of {@link BWRating }
     * 
     */
    public BWRating createBWRating() {
        return new BWRating();
    }

    /**
     * Create an instance of {@link Band }
     * 
     */
    public Band createBand() {
        return new Band();
    }

    /**
     * Create an instance of {@link DCRatedList }
     * 
     */
    public DCRatedList createDCRatedList() {
        return new DCRatedList();
    }

    /**
     * Create an instance of {@link PathPoint }
     * 
     */
    public PathPoint createPathPoint() {
        return new PathPoint();
    }

    /**
     * Create an instance of {@link TowardReferencePoint }
     * 
     */
    public TowardReferencePoint createTowardReferencePoint() {
        return new TowardReferencePoint();
    }

    /**
     * Create an instance of {@link Boundary }
     * 
     */
    public Boundary createBoundary() {
        return new Boundary();
    }

    /**
     * Create an instance of {@link ScanningRegion }
     * 
     */
    public ScanningRegion createScanningRegion() {
        return new ScanningRegion();
    }

    /**
     * Create an instance of {@link PropMapValue }
     * 
     */
    public PropMapValue createPropMapValue() {
        return new PropMapValue();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RxModel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ieee.org/DyspanSC/1900/5/SCM", name = "rxModel")
    public JAXBElement<RxModel> createRxModel(RxModel value) {
        return new JAXBElement<RxModel>(_RxModel_QNAME, RxModel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SystemModel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ieee.org/DyspanSC/1900/5/SCM", name = "systemModel")
    public JAXBElement<SystemModel> createSystemModel(SystemModel value) {
        return new JAXBElement<SystemModel>(_SystemModel_QNAME, SystemModel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TxModel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ieee.org/DyspanSC/1900/5/SCM", name = "txModel")
    public JAXBElement<TxModel> createTxModel(TxModel value) {
        return new JAXBElement<TxModel>(_TxModel_QNAME, TxModel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SCMSet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ieee.org/DyspanSC/1900/5/SCM", name = "scmSet")
    public JAXBElement<SCMSet> createScmSet(SCMSet value) {
        return new JAXBElement<SCMSet>(_ScmSet_QNAME, SCMSet.class, null, value);
    }

}
