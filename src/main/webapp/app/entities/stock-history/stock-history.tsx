import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './stock-history.reducer';

export const StockHistory = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const stockHistoryList = useAppSelector(state => state.stockHistory.entities);
  const loading = useAppSelector(state => state.stockHistory.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="stock-history-heading" data-cy="StockHistoryHeading">
        <Translate contentKey="appsalonmngApp.stockHistory.home.title">Stock Histories</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="appsalonmngApp.stockHistory.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/stock-history/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="appsalonmngApp.stockHistory.home.createLabel">Create new Stock History</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {stockHistoryList && stockHistoryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="appsalonmngApp.stockHistory.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('quantityChanged')}>
                  <Translate contentKey="appsalonmngApp.stockHistory.quantityChanged">Quantity Changed</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('quantityChanged')} />
                </th>
                <th className="hand" onClick={sort('dateTime')}>
                  <Translate contentKey="appsalonmngApp.stockHistory.dateTime">Date Time</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dateTime')} />
                </th>
                <th className="hand" onClick={sort('reason')}>
                  <Translate contentKey="appsalonmngApp.stockHistory.reason">Reason</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('reason')} />
                </th>
                <th>
                  <Translate contentKey="appsalonmngApp.stockHistory.product">Product</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {stockHistoryList.map((stockHistory, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/stock-history/${stockHistory.id}`} color="link" size="sm">
                      {stockHistory.id}
                    </Button>
                  </td>
                  <td>{stockHistory.quantityChanged}</td>
                  <td>
                    {stockHistory.dateTime ? <TextFormat type="date" value={stockHistory.dateTime} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{stockHistory.reason}</td>
                  <td>{stockHistory.product ? <Link to={`/product/${stockHistory.product.id}`}>{stockHistory.product.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/stock-history/${stockHistory.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/stock-history/${stockHistory.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (location.href = `/stock-history/${stockHistory.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="appsalonmngApp.stockHistory.home.notFound">No Stock Histories found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default StockHistory;
