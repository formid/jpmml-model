/*
 * Copyright (c) 2009 University of Tartu
 */
package org.jpmml.model;

import javax.xml.transform.sax.*;

import org.dmg.pmml.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * SAX filter for translating PMML schema version 3.X and 4.X documents to PMML schema version 4.2 documents.
 */
public class ImportFilter extends PMMLFilter {

	public ImportFilter(){
		super(Version.PMML_4_2);
	}

	public ImportFilter(XMLReader reader){
		super(reader, Version.PMML_4_2);
	}

	@Override
	public String filterLocalName(String name){

		if(("Trend").equals(name) && compare(getSource(), Version.PMML_4_0) == 0){
			return "Trend_ExpoSmooth";
		}

		return super.filterLocalName(name);
	}

	/**
	 * @param source An {@link InputSource} that contains PMML schema version 3.X or 4.X document.
	 *
	 * @return A {@link SAXSource} that contains PMML schema version 4.2 document.
	 */
	static
	public SAXSource apply(InputSource source) throws SAXException {
		XMLReader reader = XMLReaderFactory.createXMLReader();

		ImportFilter filter = new ImportFilter(reader);

		return new SAXSource(filter, source);
	}
}