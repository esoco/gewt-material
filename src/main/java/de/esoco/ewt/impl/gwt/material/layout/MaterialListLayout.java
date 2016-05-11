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

import gwt.material.design.client.constants.CollapsibleType;
import gwt.material.design.client.ui.MaterialCollapsible;

import de.esoco.ewt.component.Container;
import de.esoco.ewt.layout.GenericLayout;
import de.esoco.ewt.style.StyleData;

import com.google.gwt.user.client.ui.HasWidgets;


/********************************************************************
 * GWT Material implementation for list-style layouts.
 *
 * @author eso
 */
public class MaterialListLayout extends GenericLayout
{
	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public HasWidgets createLayoutContainer(
		Container rContainer,
		StyleData rStyle)
	{
		MaterialCollapsible aCollapsible = new MaterialCollapsible();

		aCollapsible.setType(CollapsibleType.EXPANDABLE);

		return aCollapsible;
	}
}
