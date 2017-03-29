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

import gwt.material.design.client.base.HasSelectables;
import gwt.material.design.client.base.HasSideNavHandlers;
import gwt.material.design.client.base.HasType;
import gwt.material.design.client.base.HasWaves;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.base.helper.DOMHelper;
import gwt.material.design.client.base.mixin.CssTypeMixin;
import gwt.material.design.client.constants.CssName;
import gwt.material.design.client.constants.Edge;
import gwt.material.design.client.constants.HideOn;
import gwt.material.design.client.constants.ShowOn;
import gwt.material.design.client.constants.SideNavType;
import gwt.material.design.client.events.ClearActiveEvent;
import gwt.material.design.client.events.SideNavClosedEvent;
import gwt.material.design.client.events.SideNavClosedEvent.SideNavClosedHandler;
import gwt.material.design.client.events.SideNavClosingEvent;
import gwt.material.design.client.events.SideNavClosingEvent.SideNavClosingHandler;
import gwt.material.design.client.events.SideNavOpenedEvent;
import gwt.material.design.client.events.SideNavOpenedEvent.SideNavOpenedHandler;
import gwt.material.design.client.events.SideNavOpeningEvent;
import gwt.material.design.client.events.SideNavOpeningEvent.SideNavOpeningHandler;
import gwt.material.design.client.events.SideNavPushEvent;
import gwt.material.design.client.js.JsMaterialElement;
import gwt.material.design.client.js.JsSideNavOptions;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.html.ListItem;
import gwt.material.design.jquery.client.api.JQuery;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import static gwt.material.design.client.js.JsMaterialElement.$;

//@formatter:off


/********************************************************************
 * SideNav is a material component that gives you a lists of menus and other
 * navigation components.
 *
 * <h3>UiBinder Usage:</h3>
 *
 * <pre>
   {@code
 * <m:MaterialSideNav ui:field="sideNav" width="280" m:id="mysidebar"  type="OPEN" closeOnClick="false">
 *     <m:MaterialLink href="#about" iconPosition="LEFT" iconType="OUTLINE" text="About" textColor="BLUE"  />
 *     <m:MaterialLink href="#gettingStarted" iconPosition="LEFT" iconType="DOWNLOAD" text="Getting Started" textColor="BLUE"  >
 * </m:MaterialSideNav>
 * }
 * </pre>
 *
 * @author kevzlou7979
 * @author Ben Dol
 * @see    <a
 *         href="http://gwtmaterialdesign.github.io/gwt-material-demo/#sidenavs">
 *         Material SideNav</a>
 */
//@formatter:on
@SuppressWarnings("boxing")
public class GewtMaterialSideNav extends MaterialWidget
	implements HasType<SideNavType>, HasSelectables, HasSideNavHandlers
{
	//~ Instance fields --------------------------------------------------------

	private int     width			    = 240;
	private Edge    edge			    = Edge.LEFT;
	private boolean closeOnClick	    = false;
	private boolean alwaysShowActivator = true;
	private boolean allowBodyScroll     = false;
	private boolean open;
	private Boolean showOnAttach;

	private Element activator;

	private final CssTypeMixin<SideNavType, GewtMaterialSideNav> typeMixin =
		new CssTypeMixin<>(this);

	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * Container for App Toolbar and App Sidebar , contains Material Links,
	 * Icons or any other material components.
	 */
	public GewtMaterialSideNav()
	{
		super(Document.get().createULElement(), CssName.SIDE_NAV);

		typeMixin.setType(SideNavType.FIXED);
	}

	/***************************************
	 * Creates a list and adds the given widgets.
	 *
	 * @param widgets no comment
	 */
	public GewtMaterialSideNav(final Widget... widgets)
	{
		this();

		for (final Widget w : widgets)
		{
			add(w);
		}
	}

	/***************************************
	 * Creates a new instance.
	 *
	 * @param type no comment
	 */
	@UiConstructor
	public GewtMaterialSideNav(SideNavType type)
	{
		this();
		setType(type);
	}

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * @see gwt.material.design.client.base.MaterialWidget#add(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public void add(Widget child)
	{
		super.add(wrap(child));
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasSideNavHandlers#addClosedHandler(gwt.material.design.client.events.SideNavClosedEvent$SideNavClosedHandler)
	 */
	@Override
	public HandlerRegistration addClosedHandler(SideNavClosedHandler handler)
	{
		return addHandler(handler, SideNavClosedEvent.TYPE);
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasSideNavHandlers#addClosingHandler(gwt.material.design.client.events.SideNavClosingEvent$SideNavClosingHandler)
	 */
	@Override
	public HandlerRegistration addClosingHandler(SideNavClosingHandler handler)
	{
		return addHandler(handler, SideNavClosingEvent.TYPE);
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasSideNavHandlers#addOpenedHandler(gwt.material.design.client.events.SideNavOpenedEvent$SideNavOpenedHandler)
	 */
	@Override
	public HandlerRegistration addOpenedHandler(SideNavOpenedHandler handler)
	{
		return addHandler(handler, SideNavOpenedEvent.TYPE);
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasSideNavHandlers#addOpeningHandler(gwt.material.design.client.events.SideNavOpeningEvent$SideNavOpeningHandler)
	 */
	@Override
	public HandlerRegistration addOpeningHandler(SideNavOpeningHandler handler)
	{
		return addHandler(handler, SideNavOpeningEvent.TYPE);
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasSelectables#clearActive()
	 */
	@Override
	public void clearActive()
	{
		clearActiveClass(this);
		ClearActiveEvent.fire(this);
	}

	/***************************************
	 * Returns the edge.
	 *
	 * @return The edge
	 */
	public Edge getEdge()
	{
		return edge;
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasType#getType()
	 */
	@Override
	public SideNavType getType()
	{
		return typeMixin.getType();
	}

	/***************************************
	 * @see gwt.material.design.client.base.MaterialWidget#getWidth()
	 */
	@Override
	public int getWidth()
	{
		return width;
	}

	/***************************************
	 * Hide the sidenav using the activator element
	 */
	public void hide()
	{
		$(activator).sideNav("hide");
	}

	/***************************************
	 * Hide the overlay menu.
	 */
	public void hideOverlay()
	{
		$("#sidenav-overlay").remove();
	}

	/***************************************
	 * Will the body have scroll capability while the menu is open.
	 *
	 * @return no comment
	 */
	public boolean isAllowBodyScroll()
	{
		return allowBodyScroll;
	}

	/***************************************
	 * Will the activator always be shown.
	 *
	 * @return no comment
	 */
	public boolean isAlwaysShowActivator()
	{
		return alwaysShowActivator;
	}

	/***************************************
	 * Returns the close on click.
	 *
	 * @return The close on click
	 */
	public boolean isCloseOnClick()
	{
		return closeOnClick;
	}

	/***************************************
	 * Returns the open.
	 *
	 * @return The open
	 */
	public boolean isOpen()
	{
		return open;
	}

	/***************************************
	 * Will the menu forcefully show on attachment.
	 *
	 * @return no comment
	 */
	public boolean isShowOnAttach()
	{
		return showOnAttach != null && showOnAttach;
	}

	/***************************************
	 * Reinitialize the side nav configurations when changing properties.
	 */
	public void reinitialize()
	{
		activator = null;
		initialize(false);
	}

	/***************************************
	 * Allow the body to maintain its scroll capability while the menu is
	 * visible.
	 *
	 * @param allowBodyScroll no comment
	 */
	public void setAllowBodyScroll(boolean allowBodyScroll)
	{
		this.allowBodyScroll = allowBodyScroll;
	}

	/***************************************
	 * Disable the hiding of your activator element.
	 *
	 * @param alwaysShowActivator no comment
	 */
	public void setAlwaysShowActivator(boolean alwaysShowActivator)
	{
		this.alwaysShowActivator = alwaysShowActivator;
	}

	/***************************************
	 * Close the side nav menu when an \<a\> tag is clicked from inside it. Note
	 * that if you want this to work you must wrap your item within a {@link
	 * MaterialLink}.
	 *
	 * @param closeOnClick no comment
	 */
	public void setCloseOnClick(boolean closeOnClick)
	{
		this.closeOnClick = closeOnClick;
	}

	/***************************************
	 * Set which edge of the window the menu should attach to.
	 *
	 * @param edge no comment
	 */
	public void setEdge(Edge edge)
	{
		this.edge = edge;
	}

	/***************************************
	 * @see gwt.material.design.client.base.MaterialWidget#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled)
	{
		getEnabledMixin().setEnabled(this, enabled);
	}

	/***************************************
	 * Show the menu upon attachment.<br>
	 * Note that you shouldn't apply this setting if you want your side nav to
	 * appear static. otherwise when set to <code>true</code> will slide in from
	 * the left.
	 *
	 * @param showOnAttach no comment
	 */
	public void setShowOnAttach(boolean showOnAttach)
	{
		this.showOnAttach = showOnAttach;
	}

	/***************************************
	 * Define the menu's type specification.
	 *
	 * @param type no comment
	 */
	@Override
	public void setType(SideNavType type)
	{
		typeMixin.setType(type);
	}

	/***************************************
	 * @see com.google.gwt.user.client.ui.UIObject#setWidth(java.lang.String)
	 */
	@Override
	public void setWidth(String width)
	{
		setWidth(Integer.parseInt(width));
	}

	/***************************************
	 * Set the menu's width in pixels.
	 *
	 * @param width no comment
	 */
	public void setWidth(int width)
	{
		this.width = width;
		getElement().getStyle().setWidth(width, Unit.PX);
	}

	/***************************************
	 * Show the sidenav using the activator element
	 */
	public void show()
	{
		$(activator).sideNav("show");
	}

	/***************************************
	 * no comment
	 *
	 * @param  child no comment
	 *
	 * @return no comment
	 */
	public Widget wrap(Widget child)
	{
		if (child instanceof MaterialImage)
		{
			child.getElement().getStyle()
				 .setProperty("border", "1px solid #e9e9e9");
			child.getElement().getStyle().setProperty("textAlign", "center");
		}

		// Check whether the widget is not selectable by default
		boolean isNotSelectable = false;

		if (child instanceof MaterialWidget)
		{
			MaterialWidget widget = (MaterialWidget) child;

			if (widget.getInitialClasses() != null)
			{
				if (widget.getInitialClasses().length > 0)
				{
					String initialClass = widget.getInitialClasses()[0];

					if (initialClass.contains(CssName.SIDE_PROFILE) ||
						initialClass.contains(CssName.COLLAPSIBLE))
					{
						isNotSelectable = true;
					}
				}
			}
		}

		if (!(child instanceof ListItem))
		{
			// Direct list item not collapsible
			final ListItem listItem = new ListItem();

			if (child instanceof MaterialCollapsible)
			{
				listItem.getElement().getStyle()
						.setBackgroundColor("transparent");
			}

			if (child instanceof HasWaves)
			{
				listItem.setWaves(((HasWaves) child).getWaves());
				((HasWaves) child).setWaves(null);
			}

			listItem.add(child);

			child = listItem;
		}

		// Collapsible and Side Porfile should not be selectable
		final Widget finalChild = child;

		if (!isNotSelectable)
		{
			// Active click handler
			finalChild.addDomHandler(event ->
					 				{
					 					clearActive();
					 					finalChild.addStyleName(CssName.ACTIVE);
									 },
									 ClickEvent.getType());
		}

		child.getElement().getStyle().setDisplay(Style.Display.BLOCK);

		return child;
	}

	/***************************************
	 * no comment
	 */
	protected void applyFixedType()
	{
		$(JQuery.window()).off("resize")
						  .resize((e, param1) ->
					  			{
					  				if (gwt.material.design.client.js.Window
					  					.matchMedia("all and (min-width: 992px)"))
					  				{
					  					addStyleName(CssName.OPEN);
					  				}
					  				else
					  				{
					  					removeStyleName(CssName.OPEN);
					  				}

					  				return true;
								  });
	}

	/***************************************
	 * Push the header, footer, and main to the right part when Close type is
	 * applied.
	 *
	 * @param width no comment
	 */
	protected void applyPushType(int width)
	{
		$(JQuery.window()).off("resize")
						  .resize((e, param1) ->
					  			{
					  				if (!isAlwaysShowActivator() &&
					  					!isOpen() &&
					  					gwt.material.design.client.js.Window
					  					.matchMedia("all and (min-width: 992px)"))
					  				{
					  					show();
					  				}

					  				pushElements(open, width);

					  				return true;
								  });
	}

	/***************************************
	 * no comment
	 *
	 * @param elem     no comment
	 * @param width    no comment
	 * @param duration no comment
	 */
	protected void applyTransition(Element elem, int width, int duration)
	{
		$(elem).css("transition", duration + "ms");
		$(elem).css("-moz-transition", duration + "ms");
		$(elem).css("-webkit-transition", duration + "ms");
		$(elem).css("margin-left", width + "px");
	}

	/***************************************
	 * no comment
	 */
	protected void initialize()
	{
		initialize(true);
	}

	/***************************************
	 * no comment
	 *
	 * @param strict no comment
	 */
	protected void initialize(boolean strict)
	{
		try
		{
			activator =
				DOMHelper.getElementByAttribute("data-activates", getId());

			MaterialWidget navMenu = new MaterialWidget(activator);

			navMenu.setShowOn(ShowOn.SHOW_ON_MED_DOWN);

			if (typeMixin.getType() != SideNavType.FIXED)
			{
				if (alwaysShowActivator)
				{
					navMenu.setShowOn(ShowOn.SHOW_ON_LARGE);
				}
				else
				{
					navMenu.setHideOn(HideOn.HIDE_ON_LARGE);
				}

				activator.removeClassName(CssName.NAVMENU_PERMANENT);
			}
		}
		catch (Exception ex)
		{
			if (strict)
			{
				throw new IllegalArgumentException("Could not setup MaterialSideNav please ensure you have MaterialNavBar with an activator setup to match this widgets id.");
			}
		}

		SideNavType type = getType();

		processType(type);

		JsSideNavOptions options = new JsSideNavOptions();

		options.menuWidth    = width;
		options.edge		 = edge != null ? edge.getCssName() : null;
		options.closeOnClick = closeOnClick;

		JsMaterialElement element = $(activator);

		element.sideNav(options);

		element.off("side-nav-closing");
		element.on("side-nav-closing", e1 ->
	   			{
	   				onClosing();

	   				return true;
				   });

		element.off("side-nav-closed");
		element.on("side-nav-closed", e1 ->
	   			{
	   				onClosed();

	   				return true;
				   });

		element.off("side-nav-opening");
		element.on("side-nav-opening", e1 ->
	   			{
	   				onOpening();

	   				return true;
				   });

		element.off("side-nav-opened");
		element.on("side-nav-opened", e1 ->
	   			{
	   				onOpened();

	   				return true;
				   });
	}

	/***************************************
	 * @see gwt.material.design.client.base.MaterialWidget#insert(com.google.gwt.user.client.ui.Widget,
	 *      com.google.gwt.user.client.Element, int, boolean)
	 */
	@Override
	@SuppressWarnings("deprecation")
	protected void insert(Widget							 child,
						  com.google.gwt.user.client.Element container,
						  int								 beforeIndex,
						  boolean							 domInsert)
	{
		super.insert(wrap(child), container, beforeIndex, domInsert);
	}

	/***************************************
	 * Returns the small.
	 *
	 * @return The small
	 */
	protected boolean isSmall()
	{
		return !gwt.material.design.client.js.Window.matchMedia("all and (max-width: 992px)");
	}

	/***************************************
	 * no comment
	 */
	protected void onClosed()
	{
		SideNavClosedEvent.fire(this);
	}

	/***************************************
	 * no comment
	 */
	protected void onClosing()
	{
		open = false;

		if (getType().equals(SideNavType.PUSH))
		{
			pushElements(false, width);
		}

		SideNavClosingEvent.fire(this);
	}

	/***************************************
	 * @see gwt.material.design.client.base.MaterialWidget#onLoad()
	 */
	@Override
	protected void onLoad()
	{
		super.onLoad();

		// Initialize the side nav
		initialize();

		if (showOnAttach != null)
		{
			// Ensure the side nav starts closed
			$(activator).trigger("menu-in", null);

			if (showOnAttach)
			{
				Scheduler.get()
						 .scheduleDeferred(() ->
					   					{
					   						// We are ignoring cases with mobile
					   						if (Window.getClientWidth() >
					   							960)
					   						{
					   							show();
					   						}
										   });
			}
		}
		else
		{
			if (!getType().equals(SideNavType.CARD))
			{
				setLeft(0);
			}

			$(activator).trigger("menu-out", null);
		}

		removeStyleName(SideNavType.FIXED.getCssName());
	}

	/***************************************
	 * no comment
	 */
	protected void onOpened()
	{
		if (allowBodyScroll)
		{
			RootPanel.getBodyElement().getStyle().clearOverflow();
		}

		SideNavOpenedEvent.fire(this);
	}

	/***************************************
	 * no comment
	 */
	protected void onOpening()
	{
		open = true;

		if (getType().equals(SideNavType.PUSH))
		{
			pushElements(true, width);
		}

		SideNavOpeningEvent.fire(this);
	}

	/***************************************
	 * no comment
	 *
	 * @param toggle   no comment
	 * @param width    no comment
	 * @param duration no comment
	 */
	protected void onPush(boolean toggle, int width, int duration)
	{
		SideNavPushEvent.fire(this,
							  getElement(),
							  activator,
							  toggle,
							  width,
							  duration);
	}

	/***************************************
	 * @see com.google.gwt.user.client.ui.Widget#onUnload()
	 */
	@Override
	protected void onUnload()
	{
		super.onUnload();

		$("#sidenav-overlay").remove();
		activator = null;
	}

	/***************************************
	 * no comment
	 *
	 * @param type no comment
	 */
	protected void processType(SideNavType type)
	{
		if (activator != null && type != null)
		{
			addStyleName(type.getCssName());

			switch (type)
			{
				case FIXED:
					applyFixedType();
					break;

				case MINI:
					setWidth(64);
					break;

				case CARD:
					new Timer()
						{
							@Override
							public void run()
							{
								if (isSmall())
								{
									show();
								}
							}
						}.schedule(500);
					break;

				case PUSH:
					applyPushType(width);
					break;
			}
		}
	}

	/***************************************
	 * no comment
	 *
	 * @param toggle no comment
	 * @param width  no comment
	 */
	protected void pushElements(boolean toggle, int width)
	{
		int w   = 0;
		int dur = 200;

		if (!gwt.material.design.client.js.Window.matchMedia("all and (max-width: 992px)"))
		{
			if (toggle)
			{
				w   = width;
				dur = 300;
			}

			applyTransition($("header").asElement(), w, dur);
			applyTransition($("main").asElement(), w, dur);
			applyTransition($("footer").asElement(), w, dur);
		}

		onPush(toggle, w, dur);
	}
}
