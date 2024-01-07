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

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import de.esoco.ewt.GewtMaterial;
import de.esoco.ewt.component.Container;
import de.esoco.ewt.layout.MenuLayout;
import de.esoco.ewt.style.StyleData;
import de.esoco.lib.property.Alignment;
import de.esoco.lib.property.NavigationMenuStyle;
import de.esoco.lib.property.Orientation;
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

import java.util.HashMap;
import java.util.Map;

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

	private MaterialNavBar navBar;

	private MaterialNavSection navSection;

	private MaterialFABList materialFABList;

	@Override
	public void addWidget(HasWidgets container, Widget widget, StyleData style,
		int index) {
		if (materialFABList != null) {
			materialFABList.add(widget);

			if (widget instanceof AbstractButton) {
				GewtMaterial.checkApplyButtonScale((AbstractButton) widget,
					style);
			}
		} else {
			if (widget instanceof MaterialNavSection) {
				navSection = (MaterialNavSection) widget;
			} else {
				if (!(widget instanceof MaterialNavBrand) && navBar != null &&
					navSection == null) {
					navSection = new MaterialNavSection();
					navSection.setFloat(Float.RIGHT);
					navBar.add(navSection);
				}

				if (navSection != null) {
					container = navSection;
				}
			}

			checkApplyFloat(widget, style);

			super.addWidget(container, widget, style, index);
		}
	}

	@Override
	public void clear(HasWidgets container) {
		super.clear(container);

		navBar = null;
		navSection = null;
	}

	@Override
	public HasWidgets createLayoutContainer(Container container,
		StyleData containerStyle) {
		Alignment floatAlign = containerStyle.getProperty(FLOAT, null);
		Orientation orientation =
			containerStyle.getProperty(ORIENTATION, Orientation.HORIZONTAL);
		HasWidgets menuWidget;

		if (floatAlign != null) {
			menuWidget = createFloatingMenu(containerStyle, orientation);
		} else if (orientation == Orientation.VERTICAL) {
			menuWidget = createSideMenu(containerStyle);
		} else {
			menuWidget = createTopMenu(containerStyle);
		}

		return menuWidget;
	}

	/**
	 * Creates a new floating menu.
	 *
	 * @param menuStyle   The menu style
	 * @param orientation bVertical TRUE for a vertical menu, FALSE for
	 *                    horizontal
	 * @return The menu container widget
	 */
	protected HasWidgets createFloatingMenu(StyleData menuStyle,
		Orientation orientation) {
		MaterialFAB materialFAB = new MaterialFAB();

		materialFABList = new MaterialFABList();

		materialFAB.setAxis(orientation == Orientation.VERTICAL ?
		                    Axis.VERTICAL :
		                    Axis.HORIZONTAL);

		MaterialAnchorButton menuButton =
			new MaterialAnchorButton(ButtonType.FLOATING);

		GewtMaterial.checkApplyButtonScale(menuButton, menuStyle);
		GewtMaterial.checkApplyIcon(menuButton, menuStyle);
		materialFAB.add(menuButton);
		materialFAB.add(materialFABList);

		return materialFAB;
	}

	/**
	 * Creates a menu that is displayed at the side of the target area.
	 *
	 * @param menuStyle The meniu style
	 * @return The menu container widget
	 */
	protected HasWidgets createSideMenu(StyleData menuStyle) {
		NavigationMenuStyle navigationMenuStyle =
			menuStyle.getProperty(NAVIGATION_MENU_STYLE,
				NavigationMenuStyle.OVERLAY_CONTENT);

		AbstractSideNav sideNav;

		switch (navigationMenuStyle) {
			case CARD:
				sideNav = new MaterialSideNavCard();
				break;

			case OVERLAY:
			case OVERLAY_CONTENT:
				sideNav = new MaterialSideNavDrawer();
				((MaterialSideNavDrawer) sideNav).setWithHeader(
					navigationMenuStyle == NavigationMenuStyle.OVERLAY_CONTENT);
				break;

			case PUSH:
			case PUSH_CONTENT:
				sideNav = new MaterialSideNavPush();
				((MaterialSideNavPush) sideNav).setWithHeader(
					navigationMenuStyle == NavigationMenuStyle.PUSH_CONTENT);
				break;

			case SMALL:
			case SMALL_EXPANDING:
				sideNav = new MaterialSideNavMini();
				((MaterialSideNavMini) sideNav).setExpandOnClick(
					navigationMenuStyle == NavigationMenuStyle.SMALL_EXPANDING);
				break;

			default:
				sideNav = new MaterialSideNav();
		}

		String id = menuStyle.getProperty(ELEMENT_ID, null);

		if (id != null) {
			// ID needs to be set early or else it won't be detected
			sideNav.setId(id);
		}

		sideNav.setCloseOnClick(true);
		sideNav.setShowOnAttach(false);
		sideNav.setAlwaysShowActivator(true);

		return sideNav;
	}

	/**
	 * Creates a menu at the top of the target area.
	 *
	 * @param menuStyle rStyle
	 * @return The menu container widget
	 */
	protected HasWidgets createTopMenu(StyleData menuStyle) {
		navBar = new GewtMaterialNavBar();

		String targetId = menuStyle.getProperty(TARGET_ID, null);

		if (targetId != null) {
			navBar.setActivates(targetId);
		}

		return navBar;
	}

	/**
	 * Checks whether a float alignment needs to be applied to a widget.
	 *
	 * @param widget The widget
	 * @param style  The style to check for an alignment
	 */
	private void checkApplyFloat(Widget widget, StyleData style) {
		Alignment alignment = style.getProperty(FLOAT, null);

		if (alignment != null) {
			if (alignment == Alignment.BEGIN) {
				((HasFloat) widget).setFloat(Float.LEFT);
			} else if (alignment == Alignment.END) {
				((HasFloat) widget).setFloat(Float.RIGHT);
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
