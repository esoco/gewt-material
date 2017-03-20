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

import gwt.material.design.client.base.AbstractButton;
import gwt.material.design.client.constants.Axis;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.NavBarType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialFABList;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialNavSection;
import gwt.material.design.client.ui.MaterialSideNav;

import de.esoco.ewt.GewtMaterial;
import de.esoco.ewt.component.Container;
import de.esoco.ewt.layout.MenuLayout;
import de.esoco.ewt.style.StyleData;
import de.esoco.ewt.style.StyleFlag;

import de.esoco.lib.property.Alignment;

import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import static de.esoco.lib.property.LayoutProperties.FLOAT;


/********************************************************************
 * A subclass of {@link MenuLayout} that creates material menu widgets.
 *
 * @author eso
 */
public class MaterialMenuLayout extends MenuLayout
{
	//~ Static fields/initializers ---------------------------------------------

	private static MaterialNavBar aGlobalMenu = null;

	//~ Instance fields --------------------------------------------------------

	private MaterialNavBar     aNavBar;
	private MaterialNavSection aNavSection;

	private MaterialFABList aMaterialFABList;

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void addWidget(HasWidgets rContainer,
						  Widget	 rWidget,
						  StyleData  rStyle,
						  int		 nIndex)
	{
		if (aMaterialFABList != null)
		{
			aMaterialFABList.add(rWidget);

			if (rWidget instanceof AbstractButton)
			{
				GewtMaterial.checkApplyButtonSize((AbstractButton) rWidget,
												  rStyle);
			}
		}
		else
		{
			if (aNavBar != null &&
				aNavSection == null &&
				!(rWidget instanceof MaterialNavBrand))
			{
				aNavSection = new MaterialNavSection();
				aNavSection.setFloat(Float.RIGHT);
				aNavBar.add(aNavSection);
			}

			if (aNavSection != null)
			{
				rContainer = aNavSection;
			}

			super.addWidget(rContainer, rWidget, rStyle, nIndex);
		}
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
		Alignment  eFloatAlign = rContainerStyle.getProperty(FLOAT, null);
		boolean    bVertical   = rContainerStyle.hasFlag(StyleFlag.VERTICAL);
		HasWidgets aMenuWidget;

		if (eFloatAlign != null)
		{
			MaterialFAB aMaterialFAB = new MaterialFAB();

			aMaterialFABList = new MaterialFABList();

			aMaterialFAB.setAxis(bVertical ? Axis.VERTICAL : Axis.HORIZONTAL);

			MaterialAnchorButton aMenuButton =
				new MaterialAnchorButton(ButtonType.FLOATING);

			GewtMaterial.checkApplyButtonSize(aMenuButton, rContainerStyle);
			GewtMaterial.checkApplyIcon(aMenuButton, rContainerStyle);
			aMaterialFAB.add(aMenuButton);
			aMaterialFAB.add(aMaterialFABList);

			aMenuWidget = aMaterialFAB;
		}
		else if (bVertical)
		{
			MaterialSideNav aSideNav = new GewtMaterialSideNav();

			aMenuWidget = aSideNav;

			if (aGlobalMenu != null)
			{
				aSideNav.setId("GlobalSideNav");
				aSideNav.setCloseOnClick(true);
				aSideNav.setShowOnAttach(false);
				aSideNav.setAlwaysShowActivator(true);
			}
		}
		else
		{
			aNavBar     = new GewtMaterialNavBar();
			aMenuWidget = aNavBar;

			if (aGlobalMenu == null)
			{
				aGlobalMenu = aNavBar;
				aGlobalMenu.setActivates("GlobalSideNav");
			}

			// TODO: apply style
			aNavBar.setType(NavBarType.FIXED);
		}

		return aMenuWidget;
	}

	//~ Inner Classes ----------------------------------------------------------

	/********************************************************************
	 * GEWT {@link MaterialNavBar} subclass.
	 *
	 * @author eso
	 */
	public static class GewtMaterialNavBar extends MaterialNavBar
	{
		//~ Methods ------------------------------------------------------------

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		protected void onLoad()
		{
			super.onLoad();
			getElement().setAttribute("data-activates", "GlobalSideNav");
		}
	}

	/********************************************************************
	 * TODO: DOCUMENT ME!
	 *
	 * @author eso
	 */
	public static class GewtMaterialSideNav extends MaterialSideNav
	{
	}
}
