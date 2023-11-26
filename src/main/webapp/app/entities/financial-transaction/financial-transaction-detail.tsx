import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './financial-transaction.reducer';

export const FinancialTransactionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const financialTransactionEntity = useAppSelector(state => state.financialTransaction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="financialTransactionDetailsHeading">
          <Translate contentKey="appsalonmngApp.financialTransaction.detail.title">FinancialTransaction</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{financialTransactionEntity.id}</dd>
          <dt>
            <span id="transactionType">
              <Translate contentKey="appsalonmngApp.financialTransaction.transactionType">Transaction Type</Translate>
            </span>
          </dt>
          <dd>{financialTransactionEntity.transactionType}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="appsalonmngApp.financialTransaction.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{financialTransactionEntity.amount}</dd>
          <dt>
            <span id="dateTime">
              <Translate contentKey="appsalonmngApp.financialTransaction.dateTime">Date Time</Translate>
            </span>
          </dt>
          <dd>
            {financialTransactionEntity.dateTime ? (
              <TextFormat value={financialTransactionEntity.dateTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="description">
              <Translate contentKey="appsalonmngApp.financialTransaction.description">Description</Translate>
            </span>
          </dt>
          <dd>{financialTransactionEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/financial-transaction" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/financial-transaction/${financialTransactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FinancialTransactionDetail;
