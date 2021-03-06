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
package de.esoco.ewt.impl.gwt.material.layout;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialRow;

import de.esoco.ewt.component.Container;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.LayoutType;


/********************************************************************
 * A GwtMaterial layout implementation for elements in a responsive grid.
 *
 * @author eso
 */
final class MaterialGridLayout extends AbstractMaterialLayout
{
	//~ Instance fields --------------------------------------------------------

	private LayoutType eLayout;

	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * Creates a new instance.
	 *
	 * @param eLayout The layout
	 */
	public MaterialGridLayout(LayoutType eLayout)
	{
		this.eLayout = eLayout;
	}

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	protected MaterialWidget creatMaterialLayoutContainer(
		Container rContainer,
		StyleData rContainerStyle)
	{
		MaterialWidget rContainerWidget;

		if (eLayout == LayoutType.GRID_ROW)
		{
			rContainerWidget = new MaterialRow();
		}
		else
		{
			rContainerWidget = new MaterialColumn();
		}

		return rContainerWidget;
	}
}
