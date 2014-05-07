package com.ghoome.components;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.SymbolConstants;

/**
 * Layout component for pages of application t5-blank.
 */
@Import(stylesheet = { 
	"context:static/css/bootstrap.css",
	"context:static/css/bootstrap-responsive.css",
	"context:static/css/docs.css"
})
public class Layout {
	/**
	 * The page title, for the <title> element and the <h1>element.
	 */
	@Property
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String title;
	@Property
	private String pageName;
	@Inject
	private Messages messages;
	@Inject
	private ComponentResources resources;
	@Property
	@Inject
	@Symbol(SymbolConstants.APPLICATION_VERSION)
	private String appVersion;

	public String getClassForPageName() {
		return resources.getPageName().equalsIgnoreCase(pageName) ? "active"
				: null;
	}

	public String[] getPageNames() {
		return new String[] { "Index"/*, "About"*/ };
	}
}
