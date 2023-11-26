import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Reward from './reward';
import RewardDetail from './reward-detail';
import RewardUpdate from './reward-update';
import RewardDeleteDialog from './reward-delete-dialog';

const RewardRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Reward />} />
    <Route path="new" element={<RewardUpdate />} />
    <Route path=":id">
      <Route index element={<RewardDetail />} />
      <Route path="edit" element={<RewardUpdate />} />
      <Route path="delete" element={<RewardDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RewardRoutes;
