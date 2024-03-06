import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:salonmngApp/account/login/bloc/login_bloc.dart';
import 'package:salonmngApp/account/login/login_repository.dart';
import 'package:salonmngApp/account/register/bloc/register_bloc.dart';
import 'package:salonmngApp/account/settings/settings_screen.dart';
import 'package:salonmngApp/main/bloc/main_bloc.dart';
import 'package:salonmngApp/routes.dart';
import 'package:salonmngApp/main/main_screen.dart';
import 'package:flutter/material.dart';
import 'package:salonmngApp/shared/repository/account_repository.dart';
import 'package:salonmngApp/themes.dart';
import 'account/settings/bloc/settings_bloc.dart';

import 'account/login/login_screen.dart';
import 'account/register/register_screen.dart';


// jhipster-merlin-needle-import-add - JHipster will add new imports here

class SalonmngAppApp extends StatelessWidget {
  const SalonmngAppApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'SalonmngApp app',
      theme: Themes.jhLight,
      routes: {
        SalonmngAppRoutes.login: (context) {
          return BlocProvider<LoginBloc>(
            create: (context) => LoginBloc(loginRepository: LoginRepository()),
            child: LoginScreen());
        },
        SalonmngAppRoutes.register: (context) {
          return BlocProvider<RegisterBloc>(
            create: (context) => RegisterBloc(accountRepository: AccountRepository()),
            child: RegisterScreen());
        },
        SalonmngAppRoutes.main: (context) {
          return BlocProvider<MainBloc>(
            create: (context) => MainBloc(accountRepository: AccountRepository())
              ..add(Init()),
            child: MainScreen());
        },
      SalonmngAppRoutes.settings: (context) {
        return BlocProvider<SettingsBloc>(
          create: (context) => SettingsBloc(accountRepository: AccountRepository())
            ..add(LoadCurrentUser()),
          child: SettingsScreen());
        },
        // jhipster-merlin-needle-route-add - JHipster will add new routes here
      },
    );
  }
}
