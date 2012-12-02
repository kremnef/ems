package ru.strela.ems.entityResolving;


import org.apache.cocoon.core.xml.impl.DefaultEntityResolver;
import ru.strela.ems.tools.ServerTools;

import java.io.File;


/**
 * User: hobal
 * Date: 10.09.2010
 * Time: 2:47:22
 */
public class LocalCatalogConfigurator {


	final private DefaultEntityResolver cocoonEntityResolver;


	public LocalCatalogConfigurator(final DefaultEntityResolver cocoonEntityResolver) throws Exception {
		super();
		this.cocoonEntityResolver = cocoonEntityResolver;
		cocoonEntityResolver.setLocalCatalog(getDeployedCatalogFile());
		cocoonEntityResolver.init(); // requires reinitialization
	}


	String getDeployedCatalogFile() {
         return getEntitiesDirectory() + "strela/catalog";
	}


	/**
	 * @return Cocoon entity resolver working directory with backslash at
the end
	 */
	String getEntitiesDirectory() {
		return cocoonEntityResolver.getCatalog().replaceAll("catalog$", "");
	}

}

