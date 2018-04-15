import {Component, ViewChild} from '@angular/core';
import {BestandStreamService} from "./bestandstream.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  ws = new WebSocket('ws://localhost:8080/publishwarenbewegungen/count');
  wsmongo = new WebSocket('ws://localhost:8081/subscribewarenbewegungenmongo/count');

  @ViewChild('progresschart')
  progresschart;

  publishcount: String;

  data: any;
  dataNew: any;

  published = 0;
  mongo = 0;

  res: String[];

  constructor(private bestandStreamService: BestandStreamService) {
    this.data = {
      labels: ['published', 'mongo', 'nursubscribe', 'streamprocessor', 'bestand'],
      datasets: [
        {
          label: 'Count',
          backgroundColor: '#42A5F5',
          borderColor: '#1E88E5',
          data: [0, 0, 0, 0, 0]
        },
      ]
    };

    this.ws.onmessage = (me: MessageEvent) => {
      this.publishcount = me.data;
      let json = JSON.parse(me.data);
      this.published = json.id;
      this.updatebar();
    }

    this.wsmongo.onmessage = (me: MessageEvent) => {
      this.publishcount = me.data;
      let json = JSON.parse(me.data);
      this.mongo = json.id;
      this.updatebar();
    }
  }

  updatebar() {
    this.dataNew = {
      labels: ['published', 'mongo', 'nursubscribe', 'streamprocessor', 'bestand'],
      datasets: [
        {
          label: 'Count',
          backgroundColor: '#42A5F5',
          borderColor: '#1E88E5',
          data: [0, 0, 0, 0, 0]
        },
      ]
    }
    this.data.datasets[0].data[0] = this.published;
    this.data.datasets[0].data[1] = this.mongo;
    this.progresschart.update(0)
  }

  vollepulle(event) {
    console.log(event);
    this.bestandStreamService.getVollePulle().subscribe(res => {
      console.log(res);
    })
  }

  volle1000(event) {
    console.log(event);
    this.bestandStreamService.getVolle1000().subscribe(res => {
      console.log(res);
    })
  }
}
