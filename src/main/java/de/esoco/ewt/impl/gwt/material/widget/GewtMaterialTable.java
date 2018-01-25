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

import gwt.material.design.client.data.DataSource;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;
import gwt.material.design.client.data.loader.LoadResult;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.Column;

import de.esoco.ewt.UserInterfaceContext;
import de.esoco.ewt.component.TableControl.IsTableControlWidget;
import de.esoco.ewt.impl.gwt.GewtEventDispatcher;

import de.esoco.lib.model.Callback;
import de.esoco.lib.model.ColumnDefinition;
import de.esoco.lib.model.DataModel;
import de.esoco.lib.model.RemoteDataModel;
import de.esoco.lib.model.SortableDataModel;
import de.esoco.lib.property.ContentType;
import de.esoco.lib.property.TitleAttribute;

import java.sql.Time;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import static de.esoco.lib.property.ContentProperties.CONTENT_TYPE;


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

	private MaterialDataTable<DataModel<?>> aMaterialTable;
	private MaterialDataPager<DataModel<?>> aPager = null;

	private DataModel<DataModel<?>>     rDataModel;
	private DataModel<ColumnDefinition> rColumns;

	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * Creates a new instance.
	 *
	 * @param rContext The user interface context
	 */
	public GewtMaterialTable(UserInterfaceContext rContext)
	{
		this.rContext = rContext;

		aMaterialTable = new MaterialDataTable<>();

		initWidget(aMaterialTable);
		setTableTitle(null);
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
		// TODO Add method code here
		return null;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public int getSelectionIndex()
	{
		return -1;
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

			if (aPager == null)
			{
				createPager(aDataSource);
			}
			else
			{
				aMaterialTable.setDataSource(aDataSource);
				aPager.setDataSource(aDataSource);
			}
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
	}

	/***************************************
	 * {@inheritDoc} boolean)
	 */
	@Override
	public void setSelection(int nIndex, boolean bFireEvent)
	{
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
	 * Creates the table data pager.
	 *
	 * @param aDataSource
	 */
	private void createPager(DataModelDataSource aDataSource)
	{
		aPager = new MaterialDataPager<>(aMaterialTable, aDataSource);

		aMaterialTable.add(aPager);
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
			int nLimit  = rLoadConfig.getLimit();

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
	 * Subclassed for optimizations.
	 *
	 * @author eso
	 */
	static class DataPager<T> extends MaterialDataPager<T>
	{
		//~ Constructors -------------------------------------------------------

		/***************************************
		 * Creates a new instance.
		 *
		 * @see MaterialDataPager#MaterialDataPager(MaterialDataTable, DataSource)
		 */
		public DataPager(MaterialDataTable<T> rTable, DataSource<T> rDataSource)
		{
			super(rTable, rDataSource);
		}

		//~ Methods ------------------------------------------------------------

		/***************************************
		 * @see gwt.material.design.client.ui.pager.MaterialDataPager#updateUi()
		 */
		@Override
		protected void updateUi()
		{
			super.updateUi();
		}
	}

	/********************************************************************
	 * The base class for indexed columns.
	 *
	 * @author eso
	 */
	abstract class IndexedColumn<C> extends Column<DataModel<?>, C>
	{
		//~ Instance fields ----------------------------------------------------

		private final int nIndex;

		//~ Constructors -------------------------------------------------------

		/***************************************
		 * Creates a new instance.
		 *
		 * @param rColumnDef The column definition
		 * @param rCell      The cell for rendering values
		 * @param nIndex     The column index
		 */
		public IndexedColumn(ColumnDefinition rColumnDef,
							 Cell<C>		  rCell,
							 int			  nIndex)
		{
			super(rCell);

			setName(rContext.expandResource(rColumnDef.getTitle()));
			this.nIndex = nIndex;
		}

		//~ Methods ------------------------------------------------------------

		/***************************************
		 * Returns the column index.
		 *
		 * @return The column index
		 */
		protected final int getIndex()
		{
			return nIndex;
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
	class DateColumn extends IndexedColumn<Date>
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
	class TextColumn extends IndexedColumn<String>
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
