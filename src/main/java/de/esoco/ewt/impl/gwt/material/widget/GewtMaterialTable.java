//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2018 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
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
package de.esoco.ewt.impl.gwt.material.widget;

import gwt.material.design.client.data.AbstractDataView;
import gwt.material.design.client.data.DataSource;
import gwt.material.design.client.data.DataView;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.data.SortContext;
import gwt.material.design.client.data.SortDir;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.data.infinite.HasLoader;
import gwt.material.design.client.data.infinite.InfiniteDataView;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;
import gwt.material.design.client.data.loader.LoadResult;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.Column;
import gwt.material.design.jquery.client.api.JQuery;

import de.esoco.ewt.UserInterfaceContext;
import de.esoco.ewt.component.TableControl.IsTableControlWidget;
import de.esoco.ewt.event.EventType;
import de.esoco.ewt.impl.gwt.GewtEventDispatcher;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.model.Callback;
import de.esoco.lib.model.ColumnDefinition;
import de.esoco.lib.model.DataModel;
import de.esoco.lib.model.RemoteDataModel;
import de.esoco.lib.model.SortableDataModel;
import de.esoco.lib.property.ContentType;
import de.esoco.lib.property.Indexed;
import de.esoco.lib.property.SortDirection;
import de.esoco.lib.property.StyleProperties;
import de.esoco.lib.property.TableStyle;
import de.esoco.lib.property.TitleAttribute;

import java.sql.Time;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.Range;

import static de.esoco.lib.property.ContentProperties.CONTENT_TYPE;
import static de.esoco.lib.property.StateProperties.SORT_DIRECTION;
import static de.esoco.lib.property.StyleProperties.TABLE_STYLE;


/********************************************************************
 * GWT Material implementation of {@link IsTableControlWidget}.
 *
 * @author eso
 */
public class GewtMaterialTable extends Composite
	implements IsTableControlWidget, TitleAttribute
{
	//~ Instance fields --------------------------------------------------------

	private UserInterfaceContext rContext;

	private GewtMaterialDataTable<DataModel<?>> aMaterialTable;

	private DataModel<DataModel<?>>     rDataModel;
	private DataModel<ColumnDefinition> rColumns;

	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * Creates a new instance.
	 *
	 * @param rContext The user interface context
	 * @param rStyle   The table style
	 */
	public GewtMaterialTable(UserInterfaceContext rContext, StyleData rStyle)
	{
		this.rContext = rContext;

		switch (rStyle.getProperty(TABLE_STYLE, TableStyle.FIXED))
		{
			case AUTO_LOAD:
				aMaterialTable = new GewtMaterialInfiniteDataTable<>();
				break;

			case PAGED:
				aMaterialTable = new GewtMaterialPagingDataTable<>();
				break;

			default:
				aMaterialTable = new GewtMaterialDataTable<>();
				aMaterialTable.setVisibleRange(new Range(0, 100));
		}

		initWidget(aMaterialTable);
	}

	//~ Static methods ---------------------------------------------------------

	/***************************************
	 * Returns the date time format.
	 *
	 * @param  rColumnDef sDatatype The date time format
	 *
	 * @return The date time format
	 */
	public static DateTimeFormat getDateTimeFormat(ColumnDefinition rColumnDef)
	{
		String		   sDatatype = rColumnDef.getDatatype();
		DateTimeFormat rFormat   =
			DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_SHORT);

		if (rColumnDef.getProperty(CONTENT_TYPE, null) != ContentType.DATE_TIME)
		{
			if (Date.class.getName().endsWith(sDatatype))
			{
				rFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
			}
			else if (Time.class.getName().endsWith(sDatatype))
			{
				rFormat = DateTimeFormat.getFormat(PredefinedFormat.TIME_SHORT);
			}
		}

		return rFormat;
	}

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public Widget asWidget()
	{
		return this;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public DataModel<ColumnDefinition> getColumns()
	{
		return rColumns;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public DataModel<?> getData()
	{
		return rDataModel;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public DataModel<?> getSelection()
	{
		return aMaterialTable.getSelection();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public int getSelectionIndex()
	{
		DataModel<?> rSelection = getSelection();

		return rSelection instanceof Indexed ? ((Indexed) rSelection)
											 .getIndex() : -1;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public int getTabIndex()
	{
		return aMaterialTable.getTabIndex();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public String getTableTitle()
	{
		return aMaterialTable.getTableTitle().getText();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEnabled()
	{
		return aMaterialTable.isEnabled();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void repaint()
	{
		aMaterialTable.update();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setAccessKey(char cKey)
	{
		aMaterialTable.setAccessKey(cKey);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setColumns(DataModel<ColumnDefinition> rNewColumns)
	{
		if (!rNewColumns.equals(rColumns))
		{
			rColumns = rNewColumns;

			int nColumn = 0;

			aMaterialTable.removeColumns();

			for (ColumnDefinition rColumnDef : rNewColumns)
			{
				Column<DataModel<?>, ?> aMaterialColumn;

				switch (rColumnDef.getDatatype())
				{
					case "Date":
						aMaterialColumn = new DateColumn(rColumnDef, nColumn);
						break;

					default:
						aMaterialColumn = new TextColumn(rColumnDef, nColumn);
				}

				aMaterialTable.addColumn(aMaterialColumn);
				nColumn++;
			}
		}
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void setData(DataModel<? extends DataModel<?>> rNewData)
	{
		if (rNewData != rDataModel)
		{
			rDataModel = (DataModel<DataModel<?>>) rNewData;

			DataModelDataSource aDataSource =
				new DataModelDataSource(rDataModel);

			aMaterialTable.updateDataSource(aDataSource);
			aMaterialTable.update();
		}
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setEnabled(boolean bEnabled)
	{
		aMaterialTable.setEnabled(bEnabled);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setEventDispatcher(GewtEventDispatcher rEventDispatcher)
	{
		if (aMaterialTable.getSelectionType() == SelectionType.NONE)
		{
			aMaterialTable.addRowShortPressHandler(e ->
												   rEventDispatcher
												   .dispatchEvent(EventType.SELECTION));
		}
		else
		{
			aMaterialTable.addRowSelectHandler(e ->
											   rEventDispatcher.dispatchEvent(EventType.SELECTION));
		}

		aMaterialTable.addRowDoubleClickHandler(e ->
												rEventDispatcher.dispatchEvent(EventType.ACTION));
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus(boolean bFocused)
	{
		aMaterialTable.setFocus(bFocused);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setSelection(int nIndex)
	{
		setSelection(nIndex, false);
	}

	/***************************************
	 * {@inheritDoc} boolean)
	 */
	@Override
	public void setSelection(int nIndex, boolean bFireEvent)
	{
		// currently not supported by material table
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setTabIndex(int nIndex)
	{
		aMaterialTable.setTabIndex(nIndex);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setTableTitle(String sTableTitle)
	{
		aMaterialTable.getScaffolding()
					  .getTopPanel()
					  .setVisible(sTableTitle != null);

		if (sTableTitle != null)
		{
			aMaterialTable.getTableTitle().setText(sTableTitle);
		}
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setVisibleRowCount(int nCount)
	{
		aMaterialTable.setVisibleRange(0, nCount);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	protected void initWidget(Widget rWidget)
	{
		super.initWidget(rWidget);

		setTableTitle(null);

		aMaterialTable.setSelectionType(SelectionType.SINGLE);
		aMaterialTable.addColumnSortHandler(e ->
											handleColumnSort(e.getSortContext()));
	}

	/***************************************
	 * Handles the sorting by a certain column.
	 *
	 * @param rSortContext The material sort context
	 */
	private void handleColumnSort(SortContext<DataModel<?>> rSortContext)
	{
		if (rDataModel instanceof SortableDataModel)
		{
			SortableDataModel<DataModel<?>> rSortableModel =
				(SortableDataModel<DataModel<?>>) rDataModel;

			TableColumn<?> rSortColumn =
				(TableColumn<?>) rSortContext.getSortColumn();

			rSortableModel.removeSorting();
			rSortableModel.setSortDirection(rSortColumn.getColumnDefinition()
											.getId(),
											rSortContext.getSortDir() ==
											SortDir.ASC
											? SortDirection.ASCENDING
											: SortDirection.DESCENDING);

			aMaterialTable.update();
		}
	}

	//~ Inner Classes ----------------------------------------------------------

	/********************************************************************
	 * A {@link DataSource} implementation that retrieves the data from a data
	 * model.
	 *
	 * @author eso
	 */
	public static class DataModelDataSource implements DataSource<DataModel<?>>
	{
		//~ Instance fields ----------------------------------------------------

		private DataModel<DataModel<?>> rDataModel;

		//~ Constructors -------------------------------------------------------

		/***************************************
		 * Creates a new instance.
		 *
		 * @param rDataModel The underlying data model
		 */
		@SuppressWarnings("unchecked")
		public DataModelDataSource(DataModel<DataModel<?>> rDataModel)
		{
			this.rDataModel = rDataModel;
		}

		//~ Methods ------------------------------------------------------------

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public void load(
			LoadConfig<DataModel<?>>   rLoadConfig,
			LoadCallback<DataModel<?>> rLoadCallback)
		{
			if (rDataModel instanceof RemoteDataModel)
			{
				@SuppressWarnings("unchecked")
				RemoteDataModel<DataModel<?>> rRemoteModel =
					(RemoteDataModel<DataModel<?>>) rDataModel;

				rRemoteModel.setWindow(rLoadConfig.getOffset(),
									   rLoadConfig.getLimit(),
					new Callback<RemoteDataModel<DataModel<?>>>()
					{
						@Override
						public void onSuccess(
							RemoteDataModel<DataModel<?>> rRemoteModel)
						{
							LoadResult<DataModel<?>> aResult =
								createLoadResult(rLoadConfig, rRemoteModel);

							rLoadCallback.onSuccess(aResult);
						}
						@Override
						public void onError(Throwable eError)
						{
							rLoadCallback.onFailure(eError);
						}
					});
			}
			else
			{
				LoadResult<DataModel<?>> aResult =
					createLoadResult(rLoadConfig, rDataModel);

				rLoadCallback.onSuccess(aResult);
			}
		}

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public boolean useRemoteSort()
		{
			return rDataModel instanceof SortableDataModel;
		}

		/***************************************
		 * Creates the load result for the display of certain table data.
		 *
		 * @param  rLoadConfig The load configuration
		 * @param  rModel      The model to read the data from
		 *
		 * @return The load result
		 */
		LoadResult<DataModel<?>> createLoadResult(
			LoadConfig<DataModel<?>> rLoadConfig,
			DataModel<DataModel<?>>  rModel)
		{
			int nOffset = rLoadConfig.getOffset();
			int nLimit  =
				Math.min(rLoadConfig.getLimit(), rModel.getElementCount());

			List<DataModel<?>> rData = new ArrayList<>(nLimit);

			nLimit += nOffset;

			for (int i = nOffset; i < nLimit; i++)
			{
				rData.add(rModel.getElement(i));
			}

			return new LoadResult<>(rData, nOffset, rModel.getElementCount());
		}
	}

	/********************************************************************
	 * A data table that supports infinite data scrolling.
	 *
	 * @author eso
	 */
	public static class GewtMaterialInfiniteDataTable<T>
		extends GewtMaterialDataTable<T> implements HasLoader
	{
		//~ Constructors -------------------------------------------------------

		/***************************************
		 * Creates a new instance with a dummy data source.
		 */
		public GewtMaterialInfiniteDataTable()
		{
			super(new InfiniteDataView<>(10,
					new DataSource<T>()
					{
						@Override
						public void load(
							LoadConfig<T>   loadConfig,
							LoadCallback<T> callback)
						{
						}
						@Override
						public boolean useRemoteSort()
						{
							return false;
						}
					}));
		}

		//~ Methods ------------------------------------------------------------

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public int getLoaderBuffer()
		{
			return ((InfiniteDataView<T>) view).getLoaderBuffer();
		}

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public int getLoaderDelay()
		{
			return ((InfiniteDataView<T>) view).getLoaderDelay();
		}

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public boolean isLoading()
		{
			return ((InfiniteDataView<T>) view).isLoading();
		}

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public void setLoaderBuffer(int nLoaderBuffer)
		{
			((InfiniteDataView<T>) view).setLoaderBuffer(nLoaderBuffer);
		}

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public void setLoaderDelay(int nLoaderDelay)
		{
			((InfiniteDataView<T>) view).setLoaderDelay(nLoaderDelay);
		}

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public void updateDataSource(DataSource<T> rDataSource)
		{
			super.updateDataSource(rDataSource);
		}
	}

	/********************************************************************
	 * Extended material data table.
	 *
	 * @author eso
	 */
	static class GewtMaterialDataTable<T> extends MaterialDataTable<T>
	{
		//~ Constructors -------------------------------------------------------

		/***************************************
		 * Creates a new instance with a default view.
		 */
		public GewtMaterialDataTable()
		{
		}

		/***************************************
		 * Creates a new instance with a specific data view.
		 *
		 * @param rDataView The data view
		 */
		public GewtMaterialDataTable(DataView<T> rDataView)
		{
			super(rDataView);
		}

		//~ Methods ------------------------------------------------------------

		/***************************************
		 * Returns the selection.
		 *
		 * @return The selection
		 */
		public T getSelection()
		{
			T rSelection = null;

			if (getSelectionType() == SelectionType.NONE)
			{
				Element[] aRowElements =
					getScaffolding().getTable()
									.getJsElement()
									.find("tr.data-row")
									.toArray();

				for (Element rRowElement : aRowElements)
				{
					if (JQuery.$(rRowElement).hasClass("selected"))
					{
						rSelection = getModelByRowElement(rRowElement);

						break;
					}
				}
			}
			else
			{
				List<T> rSelectedRows = getSelectedRowModels(false);

				rSelection =
					rSelectedRows.isEmpty() ? null : rSelectedRows.get(0);
			}

			return rSelection;
		}

		/***************************************
		 * Updates this table by refreshing the view.
		 */
		public void update()
		{
			DataView<T> rView = getView();

			if (rView.isSetup())
			{
				rView.setRedraw(true);
				rView.refresh();
			}
		}

		/***************************************
		 * Must be implemented by subclasses to update the source for table
		 * data.
		 *
		 * @param rDataSource The new data source
		 */
		public void updateDataSource(DataSource<T> rDataSource)
		{
			setDataSource(rDataSource);
		}

		/***************************************
		 * Copied from {@link AbstractDataView} to find the selection when the
		 * {@link SelectionType} is NONE.
		 *
		 * @param  rRowElement The row element
		 *
		 * @return The data model of the row or NULL for none
		 */
		T getModelByRowElement(Element rRowElement)
		{
			for (RowComponent<T> rRow : getView().getRows())
			{
				if (rRow.isRendered() &&
					rRow.getWidget().getElement().equals(rRowElement))
				{
					return rRow.getData();
				}
			}

			return null;
		}
	}

	/********************************************************************
	 * Extended material data table.
	 *
	 * @author eso
	 */
	static class GewtMaterialPagingDataTable<T> extends GewtMaterialDataTable<T>
	{
		//~ Instance fields ----------------------------------------------------

		private MaterialDataPager<T> aPager = null;

		//~ Constructors -------------------------------------------------------

		/***************************************
		 * Creates a new instance.
		 */
		public GewtMaterialPagingDataTable()
		{
		}

		//~ Methods ------------------------------------------------------------

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public void updateDataSource(DataSource<T> rDataSource)
		{
			super.updateDataSource(rDataSource);

			if (aPager == null)
			{
				aPager = new MaterialDataPager<>(this, rDataSource);

				aPager.setLimitOptions(5, 10, 20, 25, 50);
				add(aPager);
			}
			else
			{
				aPager.setDataSource(rDataSource);
			}
		}
	}

	/********************************************************************
	 * The base class for indexed columns.
	 *
	 * @author eso
	 */
	abstract class TableColumn<C> extends Column<DataModel<?>, C>
	{
		//~ Instance fields ----------------------------------------------------

		private ColumnDefinition rColumnDef;
		private final int		 nIndex;
		private boolean			 bSortable;

		//~ Constructors -------------------------------------------------------

		/***************************************
		 * Creates a new instance.
		 *
		 * @param rColumnDef The column definition
		 * @param rCell      The cell for rendering values
		 * @param nIndex     The column index
		 */
		public TableColumn(ColumnDefinition rColumnDef,
						   Cell<C>			rCell,
						   int				nIndex)
		{
			super(rCell);

			this.rColumnDef = rColumnDef;
			this.nIndex     = nIndex;
			this.bSortable  = rColumnDef.hasFlag(StyleProperties.SORTABLE);

			SortDirection eSortDirection =
				rColumnDef.getProperty(SORT_DIRECTION, null);

			if (eSortDirection != null)
			{
				setAutoSort(true);
				setDefaultSortAscending(eSortDirection ==
										SortDirection.ASCENDING);
			}

			setName(rContext.expandResource(rColumnDef.getTitle()));
		}

		//~ Methods ------------------------------------------------------------

		/***************************************
		 * Returns the columnDef value.
		 *
		 * @return The columnDef value
		 */
		public final ColumnDefinition getColumnDefinition()
		{
			return rColumnDef;
		}

		/***************************************
		 * Returns the column index.
		 *
		 * @return The column index
		 */
		@Override
		public final int getIndex()
		{
			return nIndex;
		}

		/***************************************
		 * @see gwt.material.design.client.ui.table.cell.Column#isSortable()
		 */
		@Override
		public boolean isSortable()
		{
			return bSortable;
		}

		/***************************************
		 * Returns the raw (unparsed) object value from a row data model.
		 *
		 * @param  rRowData The row data model
		 *
		 * @return The raw value
		 */
		protected final Object getRawValue(DataModel<?> rRowData)
		{
			return rRowData.getElement(nIndex);
		}
	}

	/********************************************************************
	 * A column that renders date values.
	 *
	 * @author eso
	 */
	class DateColumn extends TableColumn<Date>
	{
		//~ Constructors -------------------------------------------------------

		/***************************************
		 * Creates a new instance.
		 *
		 * @param rColumnDefinition The column definition
		 * @param nIndex            The column index
		 */
		public DateColumn(ColumnDefinition rColumnDefinition, int nIndex)
		{
			super(rColumnDefinition,
				  new DateCell(getDateTimeFormat(rColumnDefinition)),
				  nIndex);
		}

		//~ Methods ------------------------------------------------------------

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public Date getValue(DataModel<?> rRowData)
		{
			Object rRawValue = getRawValue(rRowData);
			Date   aDate     = null;

			if (rRawValue != null)
			{
				aDate = new Date(Long.parseLong(rRawValue.toString()));
			}

			return aDate;
		}
	}

	/********************************************************************
	 * A column that renders date values.
	 *
	 * @author eso
	 */
	class TextColumn extends TableColumn<String>
	{
		//~ Constructors -------------------------------------------------------

		/***************************************
		 * Creates a new instance.
		 *
		 * @param rColumnDefinition The column definition
		 * @param nIndex            The column index
		 */
		public TextColumn(ColumnDefinition rColumnDefinition, int nIndex)
		{
			super(rColumnDefinition, new TextCell(), nIndex);
		}

		//~ Methods ------------------------------------------------------------

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public String getValue(DataModel<?> rRowData)
		{
			Object rRawValue = getRawValue(rRowData);

			return rRawValue != null
				   ? rContext.expandResource(rRawValue.toString()) : null;
		}
	}
}
