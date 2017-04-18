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

import gwt.material.design.client.base.HasActivates;
import gwt.material.design.client.base.HasProgress;
import gwt.material.design.client.base.HasType;
import gwt.material.design.client.base.mixin.ActivatesMixin;
import gwt.material.design.client.base.mixin.CssTypeMixin;
import gwt.material.design.client.base.mixin.ProgressMixin;
import gwt.material.design.client.constants.CssName;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.NavBarType;
import gwt.material.design.client.constants.ProgressType;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.js.JsMaterialElement;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.html.Div;
import gwt.material.design.client.ui.html.Nav;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Widget;

import static gwt.material.design.jquery.client.api.JQuery.$;

//@formatter:off


/********************************************************************
 * Material NavBar represents as a app tool bar, that contains NavBrand,
 * NavSection and initialize Material Sidenav.
 *
 * <h3>UiBinder Usage:</h3>
 *
 * <pre>
   {@code
 * <m:MaterialNavBar backgroundColor="BLUE" >
 *     <m:MaterialNavBrand href="#Test" position="LEFT">Title</m:MaterialNavBrand>
 *     <m:MaterialNavSection position="RIGHT">
 *         <m:MaterialLink  iconType="ACCOUNT_CIRCLE" iconPosition="LEFT" text="Account"  textColor="WHITE" waves="LIGHT"/>
 *         <m:MaterialLink  iconType="AUTORENEW" iconPosition="LEFT" text="Refresh" textColor="WHITE" waves="LIGHT"/>
 *         <m:MaterialLink  iconType="SEARCH" tooltip="Menu" textColor="WHITE" waves="LIGHT"/>
 *          <m:MaterialLink  iconType="MORE_VERT" tooltip="Starter" textColor="WHITE" waves="LIGHT"/>
 *     </m:MaterialNavSection>
 * </m:MaterialNavBar>
 * }

   @author kevzlou7979
   @author Ben Dol
   @see <a href="http://gwtmaterialdesign.github.io/gwt-material-demo/#navbar">Material Nav Bar</a>
 </pre>
 */
//@formatter:on
public class GewtMaterialNavBar extends Nav implements HasActivates,
													   HasProgress,
													   HasType<NavBarType>
{
	//~ Instance fields --------------------------------------------------------

	private Div div = new Div();

	private MaterialLink navMenu = new MaterialLink(IconType.MENU);

	private final CssTypeMixin<NavBarType, GewtMaterialNavBar> typeMixin	  =
		new CssTypeMixin<>(this);
	private final ActivatesMixin<MaterialLink>				   activatesMixin =
		new ActivatesMixin<>(navMenu);
	private final ProgressMixin<GewtMaterialNavBar>			   progressMixin  =
		new ProgressMixin<>(this);

	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * Creates a new instance.
	 */
	public GewtMaterialNavBar()
	{
		div.setStyleName(CssName.NAV_WRAPPER);
		div.add(navMenu);
		super.add(div);
		navMenu.setFontSize(2.7, Style.Unit.EM);
		navMenu.addStyleName(CssName.BUTTON_COLLAPSE);
		navMenu.getElement().getStyle().clearDisplay();
		navMenu.setCircle(true);
		navMenu.setWaves(WavesType.LIGHT);
		navMenu.setWidth("64px");
		navMenu.setTextAlign(TextAlign.CENTER);
		navMenu.setIconPosition(IconPosition.NONE);
	}

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * @see gwt.material.design.client.base.MaterialWidget#add(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public void add(Widget child)
	{
		div.add(child);
	}

	/***************************************
	 * @see com.google.gwt.user.client.ui.Panel#clear()
	 */
	@Override
	public void clear()
	{
		div.clear();
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasActivates#getActivates()
	 */
	@Override
	public String getActivates()
	{
		return activatesMixin.getActivates();
	}

	/***************************************
	 * Returns the nav menu.
	 *
	 * @return The nav menu
	 */
	public MaterialLink getNavMenu()
	{
		return navMenu;
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasType#getType()
	 */
	@Override
	public NavBarType getType()
	{
		return typeMixin.getType();
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasProgress#hideProgress()
	 */
	@Override
	public void hideProgress()
	{
		progressMixin.hideProgress();
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasActivates#setActivates(java.lang.String)
	 */
	@Override
	public void setActivates(String activates)
	{
		activatesMixin.setActivates(activates);
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasProgress#setPercent(double)
	 */
	@Override
	public void setPercent(double percent)
	{
		progressMixin.setPercent(percent);
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasType#setType(gwt.material.design.client.constants.NavBarType)
	 */
	@Override
	public void setType(NavBarType type)
	{
		typeMixin.setType(type);
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasProgress#showProgress(gwt.material.design.client.constants.ProgressType)
	 */
	@Override
	public void showProgress(ProgressType type)
	{
		progressMixin.showProgress(type);
	}

	/***************************************
	 * DOCUMENT ME!
	 *
	 * @param type    DOCUMENT ME!
	 * @param element DOCUMENT ME!
	 */
	protected void applyType(NavBarType type, Element element)
	{
		if (type.equals(NavBarType.SHRINK))
		{
			JsMaterialElement.initShrink(element, 300);
		}
		else
		{
			GWT.log("Default type of navbar was applied");
		}
	}

	/***************************************
	 * @see gwt.material.design.client.base.MaterialWidget#onLoad()
	 */
	@Override
	protected void onLoad()
	{
		super.onLoad();

		if (typeMixin.getType() != null)
		{
			applyType(typeMixin.getType(), getElement());
		}

		// Check whether the SideNav is attached or not. If not attached Hide the NavMenu
		Element sideNavElement =
			$("#" + activatesMixin.getActivates()).asElement();

		if (sideNavElement == null)
		{
			navMenu.setVisible(false);
		}
		else
		{
			navMenu.setVisible(true);
		}
	}
}
