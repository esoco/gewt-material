//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2019 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
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
import gwt.material.design.client.base.AbstractSideNav;
import gwt.material.design.client.base.HasFloat;
import gwt.material.design.client.constants.Axis;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.SideNavType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialFABList;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialNavSection;
import gwt.material.design.client.ui.MaterialSideNav;
import gwt.material.design.client.ui.MaterialSideNavCard;
import gwt.material.design.client.ui.MaterialSideNavDrawer;
import gwt.material.design.client.ui.MaterialSideNavMini;
import gwt.material.design.client.ui.MaterialSideNavPush;

import de.esoco.ewt.GewtMaterial;
import de.esoco.ewt.component.Container;
import de.esoco.ewt.layout.MenuLayout;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.Alignment;
import de.esoco.lib.property.NavigationMenuStyle;
import de.esoco.lib.property.Orientation;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import static de.esoco.lib.property.ContentProperties.ELEMENT_ID;
import static de.esoco.lib.property.LayoutProperties.FLOAT;
import static de.esoco.lib.property.StateProperties.TARGET_ID;
import static de.esoco.lib.property.StyleProperties.NAVIGATION_MENU_STYLE;
import static de.esoco.lib.property.StyleProperties.ORIENTATION;

/**
 * A subclass of {@link MenuLayout} that creates material menu widgets.
 *
 * @author eso
 */
public class MaterialMenuLayout extends MenuLayout {

	private static final Map<NavigationMenuStyle, SideNavType> MENU_STYLES =
		new HashMap<>(NavigationMenuStyle.values().length);

	static {
		MENU_STYLES.put(NavigationMenuStyle.FIXED, SideNavType.FIXED);
		MENU_STYLES.put(NavigationMenuStyle.CARD, SideNavType.CARD);
		MENU_STYLES.put(NavigationMenuStyle.OVERLAY,
			SideNavType.DRAWER_WITH_HEADER);
		MENU_STYLES.put(NavigationMenuStyle.OVERLAY_CONTENT,
			SideNavType.DRAWER);
		MENU_STYLES.put(NavigationMenuStyle.PUSH,
			SideNavType.PUSH_WITH_HEADER);
		MENU_STYLES.put(NavigationMenuStyle.PUSH_CONTENT, SideNavType.PUSH);
		MENU_STYLES.put(NavigationMenuStyle.SMALL, SideNavType.MINI);
		MENU_STYLES.put(NavigationMenuStyle.SMALL_EXPANDING,
			SideNavType.MINI_WITH_EXPAND);
	}

	private MaterialNavBar aNavBar;

	private MaterialNavSection aNavSection;

	private MaterialFABList aMaterialFABList;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addWidget(HasWidgets rContainer, Widget rWidget,
		StyleData rStyle, int nIndex) {
		if (aMaterialFABList != null) {
			aMaterialFABList.add(rWidget);

			if (rWidget instanceof AbstractButton) {
				GewtMaterial.checkApplyButtonScale((AbstractButton) rWidget,
					rStyle);
			}
		} else {
			if (rWidget instanceof MaterialNavSection) {
				aNavSection = (MaterialNavSection) rWidget;
			} else {
				if (!(rWidget instanceof MaterialNavBrand) && aNavBar != null &&
					aNavSection == null) {
					aNavSection = new MaterialNavSection();
					aNavSection.setFloat(Float.RIGHT);
					aNavBar.add(aNavSection);
				}

				if (aNavSection != null) {
					rContainer = aNavSection;
				}
			}

			checkApplyFloat(rWidget, rStyle);

			super.addWidget(rContainer, rWidget, rStyle, nIndex);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear(HasWidgets rContainer) {
		super.clear(rContainer);

		aNavBar = null;
		aNavSection = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HasWidgets createLayoutContainer(Container rContainer,
		StyleData rContainerStyle) {
		Alignment eFloatAlign = rContainerStyle.getProperty(FLOAT, null);
		Orientation eOrientation =
			rContainerStyle.getProperty(ORIENTATION, Orientation.HORIZONTAL);
		HasWidgets aMenuWidget;

		if (eFloatAlign != null) {
			aMenuWidget = createFloatingMenu(rContainerStyle, eOrientation);
		} else if (eOrientation == Orientation.VERTICAL) {
			aMenuWidget = createSideMenu(rContainerStyle);
		} else {
			aMenuWidget = createTopMenu(rContainerStyle);
		}

		return aMenuWidget;
	}

	/**
	 * Creates a new floating menu.
	 *
	 * @param rMenuStyle   The menu style
	 * @param eOrientation bVertical TRUE for a vertical menu, FALSE for
	 *                     horizontal
	 * @return The menu container widget
	 */
	protected HasWidgets createFloatingMenu(StyleData rMenuStyle,
		Orientation eOrientation) {
		MaterialFAB aMaterialFAB = new MaterialFAB();

		aMaterialFABList = new MaterialFABList();

		aMaterialFAB.setAxis(eOrientation == Orientation.VERTICAL ?
		                     Axis.VERTICAL :
		                     Axis.HORIZONTAL);

		MaterialAnchorButton aMenuButton =
			new MaterialAnchorButton(ButtonType.FLOATING);

		GewtMaterial.checkApplyButtonScale(aMenuButton, rMenuStyle);
		GewtMaterial.checkApplyIcon(aMenuButton, rMenuStyle);
		aMaterialFAB.add(aMenuButton);
		aMaterialFAB.add(aMaterialFABList);

		return aMaterialFAB;
	}

	/**
	 * Creates a menu that is displayed at the side of the target area.
	 *
	 * @param rMenuStyle The meniu style
	 * @return The menu container widget
	 */
	protected HasWidgets createSideMenu(StyleData rMenuStyle) {
		NavigationMenuStyle eMenuStyle =
			rMenuStyle.getProperty(NAVIGATION_MENU_STYLE,
				NavigationMenuStyle.OVERLAY_CONTENT);

		AbstractSideNav aSideNav;

		switch (eMenuStyle) {
			case CARD:
				aSideNav = new MaterialSideNavCard();
				break;

			case OVERLAY:
			case OVERLAY_CONTENT:
				aSideNav = new MaterialSideNavDrawer();
				((MaterialSideNavDrawer) aSideNav).setWithHeader(
					eMenuStyle == NavigationMenuStyle.OVERLAY_CONTENT);
				break;

			case PUSH:
			case PUSH_CONTENT:
				aSideNav = new MaterialSideNavPush();
				((MaterialSideNavPush) aSideNav).setWithHeader(
					eMenuStyle == NavigationMenuStyle.PUSH_CONTENT);
				break;

			case SMALL:
			case SMALL_EXPANDING:
				aSideNav = new MaterialSideNavMini();
				((MaterialSideNavMini) aSideNav).setExpandable(
					eMenuStyle == NavigationMenuStyle.SMALL_EXPANDING);
				break;

			default:
				aSideNav = new MaterialSideNav();
		}

		String sId = rMenuStyle.getProperty(ELEMENT_ID, null);

		if (sId != null) {
			// ID needs to be set early or else it won't be detected
			aSideNav.setId(sId);
		}

		aSideNav.setCloseOnClick(true);
		aSideNav.setShowOnAttach(false);
		aSideNav.setAlwaysShowActivator(true);

		return aSideNav;
	}

	/**
	 * Creates a menu at the top of the target area.
	 *
	 * @param rMenuStyle rStyle
	 * @return The menu container widget
	 */
	protected HasWidgets createTopMenu(StyleData rMenuStyle) {
		aNavBar = new GewtMaterialNavBar();

		String sTargetId = rMenuStyle.getProperty(TARGET_ID, null);

		if (sTargetId != null) {
			aNavBar.setActivates(sTargetId);
		}

		return aNavBar;
	}

	/**
	 * Checks whether a float alignment needs to be applied to a widget.
	 *
	 * @param rWidget The widget
	 * @param rStyle  The style to check for an alignment
	 */
	private void checkApplyFloat(Widget rWidget, StyleData rStyle) {
		Alignment eAlignment = rStyle.getProperty(FLOAT, null);

		if (eAlignment != null) {
			if (eAlignment == Alignment.BEGIN) {
				((HasFloat) rWidget).setFloat(Float.LEFT);
			} else if (eAlignment == Alignment.END) {
				((HasFloat) rWidget).setFloat(Float.RIGHT);
			}
		}
	}

	/**
	 * Overridden to set as visible after onLoad().
	 *
	 * @author eso
	 */
	public static class GewtMaterialNavBar extends MaterialNavBar {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void onLoad() {
			super.onLoad();
			getNavMenu().setVisibility(Style.Visibility.VISIBLE);
		}
	}
}
