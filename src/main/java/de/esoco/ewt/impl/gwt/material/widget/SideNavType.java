//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2017 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//	  http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2016 GwtMaterialDesign
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package de.esoco.ewt.impl.gwt.material.widget;

import gwt.material.design.client.base.helper.EnumHelper;
import gwt.material.design.client.constants.CssType;


/********************************************************************
 * TODO: DOCUMENT ME!
 *
 * @author kevzlou7979
 * @author Ben Dol
 */
public enum SideNavType implements CssType
{
	FIXED("fixed"), PUSH("push"), MINI("mini"), OVERLAY("overlay"),
	FLOAT("float"), CARD("card");

	//~ Instance fields --------------------------------------------------------

	private final String cssClass;

	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * Creates a new instance.
	 *
	 * @param cssClass TODO: DOCUMENT ME!
	 */
	SideNavType(final String cssClass)
	{
		this.cssClass = cssClass;
	}

	//~ Static methods ---------------------------------------------------------

	/***************************************
	 * TODO: DOCUMENT ME!
	 *
	 * @param  styleName TODO: DOCUMENT ME!
	 *
	 * @return TODO: DOCUMENT ME!
	 */
	public static SideNavType fromStyleName(final String styleName)
	{
		return EnumHelper.fromStyleName(styleName, SideNavType.class, FIXED);
	}

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * @see com.google.gwt.dom.client.Style$HasCssName#getCssName()
	 */
	@Override
	public String getCssName()
	{
		return cssClass;
	}
}
