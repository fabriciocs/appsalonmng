import appUser from 'app/entities/app-user/app-user.reducer';
import client from 'app/entities/client/client.reducer';
import appService from 'app/entities/app-service/app-service.reducer';
import appointment from 'app/entities/appointment/appointment.reducer';
import product from 'app/entities/product/product.reducer';
import financialTransaction from 'app/entities/financial-transaction/financial-transaction.reducer';
import stockHistory from 'app/entities/stock-history/stock-history.reducer';
import reward from 'app/entities/reward/reward.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  appUser,
  client,
  appService,
  appointment,
  product,
  financialTransaction,
  stockHistory,
  reward,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
