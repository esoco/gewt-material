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

import gwt.material.design.client.base.MaterialWidget;

import de.esoco.ewt.GewtMaterial;
import de.esoco.ewt.component.Container;
import de.esoco.ewt.layout.GenericLayout;
import de.esoco.ewt.style.StyleData;

import com.google.gwt.user.client.ui.HasWidgets;


/********************************************************************
 * The base class for GwtMaterial layouts.
 *
 * @author eso
 */
public abstract class AbstractMaterialLayout extends GenericLayout
{
	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public final HasWidgets createLayoutContainer(
		Container rContainer,
		StyleData rContainerStyle)
	{
		MaterialWidget aContainer =
			creatMaterialLayoutContainer(rContainer, rContainerStyle);

		GewtMaterial.checkApplyAlignment(aContainer, rContainerStyle);

		return aContainer;
	}

	/***************************************
	 * Must be implemented by subclasses to create the actual layout container.
	 *
	 * @see GenericLayout#createLayoutContainer(Container, StyleData)
	 */
	protected abstract MaterialWidget creatMaterialLayoutContainer(
		Container rContainer,
		StyleData rContainerStyle);
}
