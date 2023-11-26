import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppUser from './app-user';
import Client from './client';
import AppService from './app-service';
import Appointment from './appointment';
import Product from './product';
import FinancialTransaction from './financial-transaction';
import StockHistory from './stock-history';
import Reward from './reward';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="app-user/*" element={<AppUser />} />
        <Route path="client/*" element={<Client />} />
        <Route path="app-service/*" element={<AppService />} />
        <Route path="appointment/*" element={<Appointment />} />
        <Route path="product/*" element={<Product />} />
        <Route path="financial-transaction/*" element={<FinancialTransaction />} />
        <Route path="stock-history/*" element={<StockHistory />} />
        <Route path="reward/*" element={<Reward />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
