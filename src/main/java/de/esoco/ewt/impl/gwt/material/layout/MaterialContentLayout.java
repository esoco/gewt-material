//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2016 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
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
package de.esoco.ewt.impl.gwt.material.layout;

import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialFooter;
import gwt.material.design.client.ui.MaterialHeader;

import de.esoco.ewt.component.Container;
import de.esoco.ewt.layout.ContentLayout;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.UserInterfaceProperties.Layout;

import com.google.gwt.user.client.ui.HasWidgets;


/********************************************************************
 * GWT Material implementation for content-specific layouts.
 *
 * @author eso
 */
public class MaterialContentLayout extends ContentLayout
{
	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * Creates a new instance.
	 *
	 * @param eLayoutType The type of the content
	 */
	public MaterialContentLayout(Layout eLayoutType)
	{
		super(eLayoutType);
	}

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public HasWidgets createLayoutContainer(
		Container rContainer,
		StyleData rStyle)
	{
		HasWidgets aLayoutWidget;

		switch (getLayoutType())
		{
			case HEADER:
				aLayoutWidget = new MaterialHeader();
				break;

			case CONTENT:
				aLayoutWidget = new MaterialContainer();
				break;

			case FOOTER:
				aLayoutWidget = new MaterialFooter();
				break;

			default:
				throw new IllegalStateException("Unsupported content layout " +
												getLayoutType());
		}

		return aLayoutWidget;
	}
}
