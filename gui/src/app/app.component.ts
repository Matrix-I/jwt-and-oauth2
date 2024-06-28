import { Component, NgModule } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ApiModule, Configuration, ConfigurationParameters } from '../../generated-client';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  imports: [RouterOutlet]
})
export class AppComponent {}

export function apiConfigFactory(): Configuration {
  const params: ConfigurationParameters = {
    basePath: 'http://localhost:4200/api'
  };
  return new Configuration(params);
}

@NgModule({
  imports: [ApiModule.forRoot(apiConfigFactory)],
  declarations: [],
  providers: [],
  bootstrap: []
})
export class AppModule {}
