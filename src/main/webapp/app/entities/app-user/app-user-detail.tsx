import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-user.reducer';

export const AppUserDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appUserEntity = useAppSelector(state => state.appUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appUserDetailsHeading">
          <Translate contentKey="appsalonmngApp.appUser.detail.title">AppUser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.id}</dd>
          <dt>
            <span id="username">
              <Translate contentKey="appsalonmngApp.appUser.username">Username</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.username}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="appsalonmngApp.appUser.password">Password</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.password}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="appsalonmngApp.appUser.email">Email</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.email}</dd>
          <dt>
            <span id="role">
              <Translate contentKey="appsalonmngApp.appUser.role">Role</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.role}</dd>
          <dt>
            <Translate contentKey="appsalonmngApp.appUser.client">Client</Translate>
          </dt>
          <dd>{appUserEntity.client ? appUserEntity.client.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/app-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-user/${appUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppUserDetail;
