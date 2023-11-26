import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reward.reducer';

export const RewardDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rewardEntity = useAppSelector(state => state.reward.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rewardDetailsHeading">
          <Translate contentKey="appsalonmngApp.reward.detail.title">Reward</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{rewardEntity.id}</dd>
          <dt>
            <span id="points">
              <Translate contentKey="appsalonmngApp.reward.points">Points</Translate>
            </span>
          </dt>
          <dd>{rewardEntity.points}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="appsalonmngApp.reward.description">Description</Translate>
            </span>
          </dt>
          <dd>{rewardEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/reward" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reward/${rewardEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RewardDetail;
