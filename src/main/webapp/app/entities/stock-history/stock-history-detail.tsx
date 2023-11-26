import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './stock-history.reducer';

export const StockHistoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const stockHistoryEntity = useAppSelector(state => state.stockHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stockHistoryDetailsHeading">
          <Translate contentKey="appsalonmngApp.stockHistory.detail.title">StockHistory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{stockHistoryEntity.id}</dd>
          <dt>
            <span id="quantityChanged">
              <Translate contentKey="appsalonmngApp.stockHistory.quantityChanged">Quantity Changed</Translate>
            </span>
          </dt>
          <dd>{stockHistoryEntity.quantityChanged}</dd>
          <dt>
            <span id="dateTime">
              <Translate contentKey="appsalonmngApp.stockHistory.dateTime">Date Time</Translate>
            </span>
          </dt>
          <dd>
            {stockHistoryEntity.dateTime ? <TextFormat value={stockHistoryEntity.dateTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="reason">
              <Translate contentKey="appsalonmngApp.stockHistory.reason">Reason</Translate>
            </span>
          </dt>
          <dd>{stockHistoryEntity.reason}</dd>
          <dt>
            <Translate contentKey="appsalonmngApp.stockHistory.product">Product</Translate>
          </dt>
          <dd>{stockHistoryEntity.product ? stockHistoryEntity.product.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/stock-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/stock-history/${stockHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StockHistoryDetail;
