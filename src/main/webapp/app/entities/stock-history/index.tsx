import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import StockHistory from './stock-history';
import StockHistoryDetail from './stock-history-detail';
import StockHistoryUpdate from './stock-history-update';
import StockHistoryDeleteDialog from './stock-history-delete-dialog';

const StockHistoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<StockHistory />} />
    <Route path="new" element={<StockHistoryUpdate />} />
    <Route path=":id">
      <Route index element={<StockHistoryDetail />} />
      <Route path="edit" element={<StockHistoryUpdate />} />
      <Route path="delete" element={<StockHistoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StockHistoryRoutes;
