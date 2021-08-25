import 'package:flutter/cupertino.dart';

import 'counter_service.dart';
import 'main.dart';

void backgroundMain() {
  WidgetsFlutterBinding.ensureInitialized();
  CounterService.instance().startCounting();
}
