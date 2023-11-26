import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-service.reducer';

export const AppServiceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appServiceEntity = useAppSelector(state => state.appService.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appServiceDetailsHeading">
          <Translate contentKey="appsalonmngApp.appService.detail.title">AppService</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{appServiceEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="appsalonmngApp.appService.name">Name</Translate>
            </span>
          </dt>
          <dd>{appServiceEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="appsalonmngApp.appService.description">Description</Translate>
            </span>
          </dt>
          <dd>{appServiceEntity.description}</dd>
          <dt>
            <span id="duration">
              <Translate contentKey="appsalonmngApp.appService.duration">Duration</Translate>
            </span>
          </dt>
          <dd>{appServiceEntity.duration}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="appsalonmngApp.appService.price">Price</Translate>
            </span>
          </dt>
          <dd>{appServiceEntity.price}</dd>
          <dt>
            <Translate contentKey="appsalonmngApp.appService.products">Products</Translate>
          </dt>
          <dd>
            {appServiceEntity.products
              ? appServiceEntity.products.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {appServiceEntity.products && i === appServiceEntity.products.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/app-service" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-service/${appServiceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppServiceDetail;
