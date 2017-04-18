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
import gwt.material.design.client.constants.SideNavType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialFABList;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialNavSection;

import de.esoco.ewt.GewtMaterial;
import de.esoco.ewt.component.Container;
import de.esoco.ewt.impl.gwt.material.widget.GewtMaterialNavBar;
import de.esoco.ewt.impl.gwt.material.widget.GewtMaterialSideNav;
import de.esoco.ewt.layout.MenuLayout;
import de.esoco.ewt.style.StyleData;
import de.esoco.ewt.style.StyleFlag;

import de.esoco.lib.property.Alignment;
import de.esoco.lib.property.NavigationMenuStyle;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import static de.esoco.lib.property.LayoutProperties.FLOAT;
import static de.esoco.lib.property.StyleProperties.NAVIGATION_MENU_STYLE;


/********************************************************************
 * A subclass of {@link MenuLayout} that creates material menu widgets.
 *
 * @author eso
 */
public class MaterialMenuLayout extends MenuLayout
{
	//~ Static fields/initializers ---------------------------------------------

	private static GewtMaterialNavBar aRecentNavBar = null;

	private static final Map<NavigationMenuStyle, SideNavType> aMenuStyles =
		new HashMap<>(NavigationMenuStyle.values().length);

	static
	{
		aMenuStyles.put(NavigationMenuStyle.FIXED, SideNavType.FIXED);
		aMenuStyles.put(NavigationMenuStyle.CARD, SideNavType.CARD);
		aMenuStyles.put(NavigationMenuStyle.OVERLAY,
						SideNavType.DRAWER_WITH_HEADER);
		aMenuStyles.put(NavigationMenuStyle.OVERLAY_CONTENT,
						SideNavType.DRAWER);
		aMenuStyles.put(NavigationMenuStyle.PUSH, SideNavType.PUSH_WITH_HEADER);
		aMenuStyles.put(NavigationMenuStyle.PUSH_CONTENT, SideNavType.PUSH);
		aMenuStyles.put(NavigationMenuStyle.SMALL, SideNavType.MINI);
		aMenuStyles.put(NavigationMenuStyle.SMALL_EXPANDING,
						SideNavType.MINI_WITH_EXPAND);
	}

	//~ Instance fields --------------------------------------------------------

	private GewtMaterialNavBar aNavBar;
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
				GewtMaterial.checkApplyButtonScale((AbstractButton) rWidget,
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

			GewtMaterial.checkApplyButtonScale(aMenuButton, rContainerStyle);
			GewtMaterial.checkApplyIcon(aMenuButton, rContainerStyle);
			aMaterialFAB.add(aMenuButton);
			aMaterialFAB.add(aMaterialFABList);

			aMenuWidget = aMaterialFAB;
		}
		else if (bVertical)
		{
			NavigationMenuStyle eMenuStyle =
				rContainerStyle.getProperty(NAVIGATION_MENU_STYLE,
											NavigationMenuStyle.OVERLAY);

			GewtMaterialSideNav aSideNav =
				new GewtMaterialSideNav(aMenuStyles.get(eMenuStyle));

			aMenuWidget = aSideNav;

			if (aRecentNavBar != null)
			{
				aSideNav.setId("GlobalSideNav");
			}

			aSideNav.setCloseOnClick(true);
			aSideNav.setShowOnAttach(false);
			aSideNav.setAlwaysShowActivator(false);
		}
		else
		{
			aNavBar		  = new GewtMaterialNavBar();
			aMenuWidget   = aNavBar;
			aRecentNavBar = aNavBar;

			aRecentNavBar.setActivates("GlobalSideNav");
		}

		return aMenuWidget;
	}
}
