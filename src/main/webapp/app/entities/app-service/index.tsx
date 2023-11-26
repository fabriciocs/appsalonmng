import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppService from './app-service';
import AppServiceDetail from './app-service-detail';
import AppServiceUpdate from './app-service-update';
import AppServiceDeleteDialog from './app-service-delete-dialog';

const AppServiceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppService />} />
    <Route path="new" element={<AppServiceUpdate />} />
    <Route path=":id">
      <Route index element={<AppServiceDetail />} />
      <Route path="edit" element={<AppServiceUpdate />} />
      <Route path="delete" element={<AppServiceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppServiceRoutes;
