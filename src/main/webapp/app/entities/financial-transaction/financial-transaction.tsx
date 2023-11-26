import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './financial-transaction.reducer';

export const FinancialTransaction = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const financialTransactionList = useAppSelector(state => state.financialTransaction.entities);
  const loading = useAppSelector(state => state.financialTransaction.loading);

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
      <h2 id="financial-transaction-heading" data-cy="FinancialTransactionHeading">
        <Translate contentKey="appsalonmngApp.financialTransaction.home.title">Financial Transactions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="appsalonmngApp.financialTransaction.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/financial-transaction/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="appsalonmngApp.financialTransaction.home.createLabel">Create new Financial Transaction</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {financialTransactionList && financialTransactionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="appsalonmngApp.financialTransaction.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('transactionType')}>
                  <Translate contentKey="appsalonmngApp.financialTransaction.transactionType">Transaction Type</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('transactionType')} />
                </th>
                <th className="hand" onClick={sort('amount')}>
                  <Translate contentKey="appsalonmngApp.financialTransaction.amount">Amount</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('amount')} />
                </th>
                <th className="hand" onClick={sort('dateTime')}>
                  <Translate contentKey="appsalonmngApp.financialTransaction.dateTime">Date Time</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dateTime')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="appsalonmngApp.financialTransaction.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {financialTransactionList.map((financialTransaction, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/financial-transaction/${financialTransaction.id}`} color="link" size="sm">
                      {financialTransaction.id}
                    </Button>
                  </td>
                  <td>{financialTransaction.transactionType}</td>
                  <td>{financialTransaction.amount}</td>
                  <td>
                    {financialTransaction.dateTime ? (
                      <TextFormat type="date" value={financialTransaction.dateTime} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{financialTransaction.description}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/financial-transaction/${financialTransaction.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/financial-transaction/${financialTransaction.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (location.href = `/financial-transaction/${financialTransaction.id}/delete`)}
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
              <Translate contentKey="appsalonmngApp.financialTransaction.home.notFound">No Financial Transactions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default FinancialTransaction;
