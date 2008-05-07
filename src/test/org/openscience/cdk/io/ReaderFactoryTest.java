/* $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 *
 * Copyright (C) 2003-2007  The Chemistry Development Kit (CDK) project
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *  */
package org.openscience.cdk.io;

import java.io.InputStream;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.openscience.cdk.CDKTestCase;
import org.openscience.cdk.ChemFile;
import org.openscience.cdk.ChemModel;
import org.openscience.cdk.ChemObject;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.Reaction;
import org.openscience.cdk.io.formats.ABINITFormat;
import org.openscience.cdk.io.formats.ADFFormat;
import org.openscience.cdk.io.formats.Aces2Format;
import org.openscience.cdk.io.formats.CMLFormat;
import org.openscience.cdk.io.formats.CTXFormat;
import org.openscience.cdk.io.formats.GamessFormat;
import org.openscience.cdk.io.formats.Gaussian92Format;
import org.openscience.cdk.io.formats.Gaussian94Format;
import org.openscience.cdk.io.formats.Gaussian98Format;
import org.openscience.cdk.io.formats.GhemicalSPMFormat;
import org.openscience.cdk.io.formats.IChemFormat;
import org.openscience.cdk.io.formats.IChemFormatMatcher;
import org.openscience.cdk.io.formats.INChIFormat;
import org.openscience.cdk.io.formats.INChIPlainTextFormat;
import org.openscience.cdk.io.formats.IResourceFormat;
import org.openscience.cdk.io.formats.JaguarFormat;
import org.openscience.cdk.io.formats.MDLFormat;
import org.openscience.cdk.io.formats.MDLV2000Format;
import org.openscience.cdk.io.formats.MDLV3000Format;
import org.openscience.cdk.io.formats.Mol2Format;
import org.openscience.cdk.io.formats.PDBFormat;
import org.openscience.cdk.io.formats.PubChemASNFormat;
import org.openscience.cdk.io.formats.PubChemCompoundXMLFormat;
import org.openscience.cdk.io.formats.PubChemCompoundsXMLFormat;
import org.openscience.cdk.io.formats.PubChemSubstanceXMLFormat;
import org.openscience.cdk.io.formats.PubChemSubstancesASNFormat;
import org.openscience.cdk.io.formats.PubChemSubstancesXMLFormat;
import org.openscience.cdk.io.formats.ShelXFormat;
import org.openscience.cdk.io.formats.VASPFormat;
import org.openscience.cdk.io.formats.XYZFormat;
import org.openscience.cdk.tools.LoggingTool;

/**
 * TestCase for the reading CML files using a few test files
 * in data/cmltest as found in the Jmol distribution
 * (http://jmol.sf.org/).
 *
 * @cdk.module test-io
 */
public class ReaderFactoryTest extends CDKTestCase {

    private ReaderFactory factory;
    private LoggingTool logger;
    
    public ReaderFactoryTest(String name) {
        super(name);
        logger = new LoggingTool(this);
        factory = new ReaderFactory();
    }

    public static Test suite() {
        return new TestSuite(ReaderFactoryTest.class);
    }

    public void testCreateReader_IChemFormat() {
    	IChemFormat format = (IChemFormat)XYZFormat.getInstance();
        ISimpleChemObjectReader reader = factory.createReader(format);
        assertNotNull(reader);
        assertEquals(format.getFormatName(), reader.getFormat().getFormatName());
    }
    public void testGaussian94() throws Exception {
        expectFormat("data/gaussian/4-cyanophenylnitrene-Benzazirine-TS.g94.out", 
                     Gaussian94Format.getInstance());
    }
    public void testGaussian98() throws Exception {
        expectReader("data/gaussian/g98.out", Gaussian98Format.getInstance());
    }
    public void testGaussian92() throws Exception {
        expectFormat("data/gaussian/phenylnitrene.g92.out", Gaussian92Format.getInstance());
    }

    public void testGhemical() throws Exception {
        expectReader("data/ghemical/ethene.mm1gp", GhemicalSPMFormat.getInstance());
    }

    public void testJaguar() throws Exception {
        expectFormat("data/jaguar/ch4-opt.out", JaguarFormat.getInstance());
    }

    public void testINChI() throws Exception {
        expectReader("data/inchi/guanine.inchi.xml", INChIFormat.getInstance());
    }

    public void testINChIPlainText() throws Exception {
        expectReader("data/inchi/guanine.inchi", INChIPlainTextFormat.getInstance());
    }

    public void testVASP() throws Exception {
        expectReader("data/vasp/LiMoS2_optimisation_ISIF3.vasp", VASPFormat.getInstance());
    }

    public void testAces2() throws Exception {
        expectFormat("data/aces2/ch3oh_ace.out", Aces2Format.getInstance());
    }

    public void testADF() throws Exception {
        expectFormat("data/adf/ammonia.adf.out", ADFFormat.getInstance());
    }

    public void testGamess() throws Exception {
        expectReader("data/gamess/ch3oh_gam.out", GamessFormat.getInstance());
    }

    public void testABINIT() throws Exception {
        expectFormat("data/abinit/t54.in", ABINITFormat.getInstance());
    }

    public void testCML() throws Exception {
        expectReader("data/cml/estron.cml", CMLFormat.getInstance());
    }

    public void testXYZ() throws Exception {
        expectReader("data/xyz/bf3.xyz", XYZFormat.getInstance());
    }

    public void testShelX() throws Exception {
        expectReader("data/shelx/frame_1.res", ShelXFormat.getInstance());
    }
    
    public void testMDLMol() throws Exception {
        expectReader("data/mdl/bug1014344-1.mol", MDLFormat.getInstance());
    }

    public void testMDLMolV2000() throws Exception {
        expectReader("data/mdl/methylbenzol.mol", MDLV2000Format.getInstance());
    }
    
    public void testDetection() throws Exception {
    	expectReader("data/mdl/withcharges.mol", MDLV2000Format.getInstance());
    }

    public void testMDLMolV3000() throws Exception {
        expectReader("data/mdl/molV3000.mol", MDLV3000Format.getInstance());
    }

    public void testPDB() throws Exception {
        expectReader("data/pdb/coffeine.pdb", PDBFormat.getInstance());
    }
    
    public void testMol2() throws Exception {
    	expectReader("data/mol2/fromWebsite.mol2", Mol2Format.getInstance());
    }
    
    public void testCTX() throws Exception {
    	expectReader("data/ctx/methanol_with_descriptors.ctx", CTXFormat.getInstance());
    }
    
    public void testPubChemCompoundASN() throws Exception {
        expectReader("data/asn/pubchem/cid1.asn", PubChemASNFormat.getInstance());
    }

    public void testPubChemSubstancesASN() throws Exception {
        expectFormat("data/asn/pubchem/list.asn", PubChemSubstancesASNFormat.getInstance());
    }

    public void testPubChemCompoundsXML() throws Exception {
        expectFormat("data/asn/pubchem/aceticAcids38.xml", PubChemCompoundsXMLFormat.getInstance());
    }
    
    public void testPubChemSubstancesXML() throws Exception {
        expectFormat("data/asn/pubchem/taxols.xml", PubChemSubstancesXMLFormat.getInstance());
    }
    
    public void testPubChemSubstanceXML() throws Exception {
        expectReader("data/asn/pubchem/sid577309.xml", PubChemSubstanceXMLFormat.getInstance());
    }
    
    public void testPubChemCompoundXML() throws Exception {
        expectReader("data/asn/pubchem/cid1145.xml", PubChemCompoundXMLFormat.getInstance());
    }
    
    private void expectFormat(String filename, IResourceFormat expectedFormat) throws Exception {
        InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);
        if (ins == null) {
            fail("Cannot find file: " + filename);
        }
        if (expectedFormat instanceof IChemFormatMatcher) {
        	factory.registerFormat((IChemFormatMatcher)expectedFormat);
        }
        IChemFormat format = factory.guessFormat(ins);
        assertNotNull(format);
        assertEquals(expectedFormat.getFormatName(), format.getFormatName());
    }
    private void expectReader(String filename, IResourceFormat expectedFormat) throws Exception {
        InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);
        if (ins == null) {
            fail("Cannot find file: " + filename);
        }
        if (expectedFormat instanceof IChemFormatMatcher) {
        	factory.registerFormat((IChemFormatMatcher)expectedFormat);
        }
        IChemFormat format = factory.guessFormat(ins);
        assertNotNull(format);
        assertEquals(expectedFormat.getFormatName(), format.getFormatName());
        // ok, if format ok, try instantiating a reader
        ins = this.getClass().getClassLoader().getResourceAsStream(filename);
        ISimpleChemObjectReader reader = factory.createReader(ins);
        assertNotNull(reader);
        assertEquals(format.getReaderClassName(), reader.getClass().getName());
        // now try reading something from it
        ChemObject[] objects = { 
        		new ChemFile(), new ChemModel(), new Molecule(),
        		new Reaction()
        };
        boolean read = false;
        for (int i=0; (i<objects.length && !read); i++) {
        	if (reader.accepts(objects[i].getClass())) {
        		reader.read(objects[i]);
        		read = true;
        	}
        }
        if (read) {
        	// ok, reseting worked
        } else {
        	fail("Reading an IChemObject from the Reader did not work properly.");
        }
    }
    
}
