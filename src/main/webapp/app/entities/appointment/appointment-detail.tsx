import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './appointment.reducer';

export const AppointmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appointmentEntity = useAppSelector(state => state.appointment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appointmentDetailsHeading">
          <Translate contentKey="appsalonmngApp.appointment.detail.title">Appointment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{appointmentEntity.id}</dd>
          <dt>
            <span id="dateTime">
              <Translate contentKey="appsalonmngApp.appointment.dateTime">Date Time</Translate>
            </span>
          </dt>
          <dd>
            {appointmentEntity.dateTime ? <TextFormat value={appointmentEntity.dateTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="status">
              <Translate contentKey="appsalonmngApp.appointment.status">Status</Translate>
            </span>
          </dt>
          <dd>{appointmentEntity.status}</dd>
          <dt>
            <Translate contentKey="appsalonmngApp.appointment.client">Client</Translate>
          </dt>
          <dd>{appointmentEntity.client ? appointmentEntity.client.id : ''}</dd>
          <dt>
            <Translate contentKey="appsalonmngApp.appointment.appService">App Service</Translate>
          </dt>
          <dd>{appointmentEntity.appService ? appointmentEntity.appService.id : ''}</dd>
          <dt>
            <Translate contentKey="appsalonmngApp.appointment.appUser">App User</Translate>
          </dt>
          <dd>{appointmentEntity.appUser ? appointmentEntity.appUser.username : ''}</dd>
        </dl>
        <Button tag={Link} to="/appointment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/appointment/${appointmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppointmentDetail;
