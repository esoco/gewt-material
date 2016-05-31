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

import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialNavSection;
import gwt.material.design.client.ui.MaterialSideNav;

import de.esoco.ewt.component.Container;
import de.esoco.ewt.layout.MenuLayout;
import de.esoco.ewt.style.StyleData;
import de.esoco.ewt.style.StyleFlag;

import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;


/********************************************************************
 * A subclass of {@link MenuLayout} that creates material menu widgets.
 *
 * @author eso
 */
public class MaterialMenuLayout extends MenuLayout
{
	//~ Instance fields --------------------------------------------------------

	private MaterialNavBar     aNavBar;
	private MaterialNavSection aNavSection;

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void addWidget(HasWidgets rContainer,
						  Widget	 rWidget,
						  StyleData  rStyleData,
						  int		 nIndex)
	{
		if (aNavBar != null &&
			aNavSection == null &&
			!(rWidget instanceof MaterialNavBrand))
		{
			aNavSection = new MaterialNavSection();
			aNavSection.setFloat(Float.RIGHT);
			aNavBar.add(aNavSection);
		}

		super.addWidget(aNavSection != null ? aNavSection : rContainer,
						rWidget,
						rStyleData,
						nIndex);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void clear(HasWidgets rContainer)
	{
		super.clear(rContainer);

		aNavBar     = null;
		aNavSection = null;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public HasWidgets createLayoutContainer(
		Container rContainer,
		StyleData rContainerStyle)
	{
		HasWidgets aMenuWidget;

		if (rContainerStyle.hasFlag(StyleFlag.VERTICAL))
		{
			aMenuWidget = new MaterialSideNav();
		}
		else
		{
			aNavBar     = new MaterialNavBar();
			aMenuWidget = aNavBar;
		}

		return aMenuWidget;
	}
}
